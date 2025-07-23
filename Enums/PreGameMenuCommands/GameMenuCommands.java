package com.untildawn.Enums.PreGameMenuCommands;

import com.untildawn.Enums.GameMenus.MenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameMenuCommands implements MenuCommands {

    Exit_Menu("menu\\s+exit"),
    NEW_GAME("^\\s*game\\s+new\\s*$"),
    ;


    private final String pattern;

    GameMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
