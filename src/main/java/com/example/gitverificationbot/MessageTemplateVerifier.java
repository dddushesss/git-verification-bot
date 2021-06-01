package com.example.gitverificationbot;

import java.util.regex.Pattern;

public class MessageTemplateVerifier
{
    public static final String VERIFICATION_RESULT = "Commit test result:\n";
    public static final String OK_RESULT = "OK";
    public static final String ERROR_RESULT =
            "ERROR!\n" +
                    "Commit must start with prefix LEETCODE or GENERATOR\n" +
                    "Commit must contain group number (ex: 1022)\n" +
                    "Commit must have action message (Added, Deleted, Refactored, Moved, Fixed)";
    public static final String regx = "^(GENERATOR|LEETCODE)\\s(2021|2022|1021|1022)\\s(Added|Fixed|Refactored|Deleted|Moved)\\s.+";

    private static boolean checkCommit(String message){
        return Pattern.matches(regx, message);
    }

    public static String process(String message){
        if(checkCommit(message)){
            return VERIFICATION_RESULT + OK_RESULT;
        }
        else {
            return VERIFICATION_RESULT + ERROR_RESULT;
        }
    }
}
