package com.untildawn.Enums.PreGameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands {
    Go_To_AvatarMenu("menu\\s+enter\\s+avatar\\s+menu"),
    Go_To_ProfileMenu("menu\\s+enter\\s+profile\\s+menu"),
    Go_To_GameMenu("menu\\s+enter\\s+game\\s+menu"),
    Current_Menu("show\\s+current\\s+menu"),
    User_Logout("user\\s+logout"),
    Exit_Menu("menu\\s+exit");

    private final String pattern;

    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
