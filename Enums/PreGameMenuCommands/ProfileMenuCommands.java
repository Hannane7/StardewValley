package com.untildawn.Enums.PreGameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProfileMenuCommands {
    Change_Username("change\\s+username\\s+-u\\s+(?<username>.+)"),
    Username_Validation("[a-zA-Z0-9\\-]+"),
    Change_NickName("change\\s+nickname\\s+-u\\s+(?<nickname>.+)"),
    Change_Email("change\\s+email\\s+-e\\s+(?<email>.+)"),
    Email_Validation("^(?!.*\\.\\.)[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?@(?=[a-zA-Z0-9])" +
            "[a-zA-Z0-9.-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}$"),
    Change_Password("change\\s+password\\s+-p\\s+(?<newPassword>.+)\\s+-o\\s+(?<oldPassword>.+)"),
    Password_Validation("^[a-zA-Z0-9?><,\"';:/\\\\|\\]\\[}{+=)(*&^%$#!]*$"),
    Current_Menu("show\\s+current\\s+menu"),
    Menu_Exit("menu\\s+exit"),
    Go_To_MainMenu("menu\\s+enter\\s+main\\s+menu"),
    Exit_Menu("menu\\s+exit"),
    User_Info("user\\s+info");

    private final String pattern;

    ProfileMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
