package com.untildawn.Enums.InGameMenuCommands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CookingMenuCommands {
    SWITCH_MENU("^\\s*switch\\s+menu\\s*$"),
    COOKING_REFRIGERATOR_PUT("^\\s*cooking\\s+refrigerator\\s+put\\s+(?<item>.+?)\\s*$"),
    COOKING_REFRIGERATOR_PICK("^\\s*cooking\\s+refrigerator\\s+pick\\s+(?<item>.+?)\\s*$"),
    COOKING_SHOW_RECIPES("^\\s*cooking\\s+show\\s+recipes\\s*$"),
    COOKING_PREPARE("^\\s*cooking\\s+prepare\\s+(?<recipeName>.+?)\\s*$"),
    EAT("^\\s*eat\\s+(?<foodName>.+?)\\s*$");



    private final String pattern;

    CookingMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
