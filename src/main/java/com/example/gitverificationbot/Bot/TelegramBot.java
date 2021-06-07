package com.example.gitverificationbot.Bot;


import com.example.gitverificationbot.Bot.Common.TelegramBotHandler;
import com.example.gitverificationbot.Services.DatabaseService;
import com.example.gitverificationbot.Services.GithubClient;
import com.example.gitverificationbot.Bot.Config.BotConfig;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final DatabaseService databaseService;
    private final GithubClient githubClient;

    public TelegramBot(DatabaseService databaseService, GithubClient githubClient) {
        this.databaseService = databaseService;
        this.githubClient = githubClient;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.getInstance().getLOGIN();
    }

    @Override
    public String getBotToken() {
        return BotConfig.getInstance().getTOKEN();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            TelegramBotHandler handler = new TelegramBotHandler(update, databaseService, githubClient);
            execute(handler.keyboardHandler(update));
        } else if (update.hasCallbackQuery()) {
            TelegramBotHandler handler = new TelegramBotHandler(update, databaseService, githubClient);
            execute(handler.callbackHandler(update));
        }
    }
}

