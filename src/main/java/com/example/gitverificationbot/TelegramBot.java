package com.example.gitverificationbot;


import com.example.gitverificationbot.Model.Issue;
import com.example.gitverificationbot.Model.Student;
import com.example.gitverificationbot.Services.DatabaseService;
import com.example.gitverificationbot.Services.GithubClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private DatabaseService databaseService;
    @Autowired
    private GithubClient githubClient;
    private final String TOKEN = System.getenv("telegram_bot_token");
    private final String LOGIN = System.getenv("telegram_bot_login");
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    private void initKeyboard() {
        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.add(new KeyboardButton("Вывести всех студентов"));
        keyboardRow.add(new KeyboardButton("Проверить все репозитории"));
        keyboardRow.add(new KeyboardButton("Удалить все коменты"));
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton("Добавить студента"));
        keyboardRow2.add(new KeyboardButton("Удалить студента"));
        keyboardRowList.add(keyboardRow);
        keyboardRowList.add(keyboardRow2);
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        replyKeyboardMarkup.setResizeKeyboard(true);
    }

    @Override
    public String getBotUsername() {
        return LOGIN;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    private String checkRepos() {
        StringBuilder result = new StringBuilder();

        databaseService.getStudents().forEach(student -> {
            String repo = student.getRepository().substring(student.getRepository().lastIndexOf("/") + 1);
            try {
                githubClient.getPullRequests(student.getGitLogin(), repo).forEach(pull -> {
                    try {
                        githubClient.createIssueComm(new Issue(MessageTemplateVerifier.process(pull.getTitle())), student.getGitLogin(), repo, pull.getNumber());
                    } catch (IOException e) {
                        result.append(e.getMessage()).append("\n");
                    }
                });
            } catch (IOException e) {
                result.append(e.getMessage()).append("\n");
            }
            result.append("Проверен студент ").append(student.getFirs_Name()).append(" ").append(student.getLast_Name()).append("\n");
        });

        return result.toString();
    }

    private String getStudents() {
        StringBuilder result = new StringBuilder();
        if (databaseService.getStudents().size() == 0) {
            return "Нет студентов в базе";
        }
        databaseService.getStudents().forEach(x -> result.append(x.getFirs_Name())
                .append(" ")
                .append(x.getLast_Name()).append(" ")
                .append(x.getGitLogin()).append('\t')
                .append(x.getRepository()).append('\n'));
        return result.toString();
    }

    private String deleteAllComments() {
        StringBuilder result = new StringBuilder();
        databaseService.getStudents()
                .forEach(student -> {
                    String repo = student.getRepository().substring(student.getRepository().lastIndexOf("/") + 1);
                    try {
                        githubClient.getRepoIssues(student.getGitLogin(), repo).forEach(issue -> {
                            try {
                                githubClient.deleteReviews(student.getGitLogin(), repo, issue.getId());
                            } catch (IOException e) {
                                result.append(e.getMessage()).append("\n\n");
                            }
                        });
                    } catch (IOException e) {
                        result.append(e.getMessage()).append("\n\n");
                    }
                });

        result.append("Удаление закончено");
        return result.toString();
    }

    private void DeleteStudent(SendMessage message) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        if (databaseService.getStudents().size() == 0) {
            message.setText("В базе нет студентов");
            return;
        }
        databaseService.getStudents().forEach(x -> {
            InlineKeyboardButton keyboardButton = new InlineKeyboardButton(x.getFirs_Name() + " " + x.getLast_Name() + "\t" + x.getRepository());
            keyboardButton.setCallbackData("ToDelete " + x.getId() + " " + message.getChatId());
            List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
            keyboardButtons.add(keyboardButton);
            rowsInline.add(keyboardButtons);
        });

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        message.setText("Выберете студента: ");
    }

    private SendMessage keyboardHandler(Update update) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(update.getMessage().getChatId()));
        String msg = update.getMessage().getText();
        switch (msg) {
            case "Вывести всех студентов":
                sendMessage.setText(getStudents());
                sendMessage.disableWebPagePreview();
                break;
            case "/start":
                initKeyboard();
                sendMessage.setReplyMarkup(replyKeyboardMarkup);
                sendMessage.setText("Тут можно докапываться до студентов");
                break;
            case "Проверить все репозитории":
                sendMessage.setText(checkRepos());
                break;
            case "Удалить все коменты":
                sendMessage.setText(deleteAllComments());
                break;
            case "Удалить студента":
                DeleteStudent(sendMessage);
                break;
            case "Добавить студента":
                sendMessage.setText("Введите Имя Фамилию Логин гитхаба и репозиторий");
                break;
            default:
                if (msg.split(" ").length == 4) {
                    databaseService.addStudent(String.join("','", msg
                            .split(" ")));
                    sendMessage.setText("Студент добавлен");
                } else {
                    sendMessage.setText("Я не понимат");
                }
                break;
        }
        return sendMessage;
    }

    private EditMessageText callbackHandler(Update update) {
        String[] data = update.getCallbackQuery().getData().split(" ");
        EditMessageText editMessageText = new EditMessageText();
        switch (data[0]) {
            case "ToDelete":
                Student studentToDelete = databaseService.getStudentById(Integer.parseInt(data[1]));
                editMessageText.setText("Студент "
                        + studentToDelete.getFirs_Name() + " " + studentToDelete.getLast_Name()
                        + " удалён");
                databaseService.deleteStudent(Integer.parseInt(data[1]));
                editMessageText.setChatId(data[2]);
                editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                break;
            default:
                editMessageText.setText("Ошибка: студент не найден в базе");
                editMessageText.setChatId(data[2]);
                editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                break;
        }
        return editMessageText;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            execute(keyboardHandler(update));
        } else if (update.hasCallbackQuery()) {
            execute(callbackHandler(update));
        }
    }
}

