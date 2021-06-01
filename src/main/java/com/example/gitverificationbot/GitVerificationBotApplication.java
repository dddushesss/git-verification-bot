package com.example.gitverificationbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GitVerificationBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitVerificationBotApplication.class, args);
    }

}
