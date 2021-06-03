package com.example.gitverificationbot.Bot.Config;

import lombok.Data;

@Data
public class BotConfig {

    private final String TOKEN;
    private final String LOGIN;
    private static volatile BotConfig instance;

    private BotConfig() {
        TOKEN = System.getenv("telegram_bot_token");
        LOGIN = System.getenv("telegram_bot_login");
    }

    public static BotConfig getInstance(){
        BotConfig localInstance = instance;
        if (localInstance == null) {
            synchronized (BotConfig.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new BotConfig();
                }
            }
        }
        return localInstance;
    }
}
