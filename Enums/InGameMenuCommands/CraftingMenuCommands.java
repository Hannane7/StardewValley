package com.untildawn.Enums.InGameMenuCommands;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum CraftingMenuCommands {
    SWITCH_MENU("^\\s*switch\\s+menu\\s*$"),
    CRAFTING_SHOW_RECIPES("^\\s*crafting\\s+show\\s+recipes\\s*$"),
    CRAFTING_CRAFT("^\\s*crafting\\s+craft\\s+(?<itemName>.+?)\\s*$"),
    PLACE_ITEM("^\\s*place\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-d\\s+(?<direction>.+?)\\s*$"),
    CHEAT_ADD_ITEM("^\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+?)\\s+-c\\s+(?<count>.+?)\\s*$");


    private final String pattern;

    CraftingMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
