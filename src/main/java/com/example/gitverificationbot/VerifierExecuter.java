package com.example.gitverificationbot;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VerifierExecuter {


    //@Scheduled(cron = "*/5 * * * * *")
    public void verify() {
        System.out.printf("Влад Котов, где ТЗ? Ты же обещал\n");
    }
}
