package com.untildawn.Enums.PreGameMenuCommands;

import com.untildawn.Enums.GameMenus.MenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginMenuCommands implements MenuCommands {
    CHANGE_MENU("^\\s*menu\\s+enter\\s+(?<menu>.+?)\\s*$"),
    MENU_EXIT("^\\s*menu\\s+exit\\s*$"),
    SHOW_CURRENT_MENU("^\\s*show\\s+current\\s+menu\\s*$"),
    LOGIN("^\\s*login\\s+-u\\s+(?<username>\\S+)\\s+-p\\s+(?<password>\\S+)(\\s+--?(?<stayLoggedIn>stay-logged-in))?\\s*$"),
    FORGET_PASSWORD("^\\s*forget\\s+password\\s+-u\\s+(?<username>.+?)\\s*$"),
    ANSWER("^\\s*answer\\s+-a\\s+(?<answer>.+?)\\s*$");

    private final String pattern;

    LoginMenuCommands(String pattern) {
        this.pattern = pattern;
    }
    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;

    }
}
