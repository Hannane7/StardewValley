package com.untildawn.Enums.InGameMenuCommands;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum InventoryCommands {
    SWITCH_MENU("^\\s*switch\\s+menu\\s*$"),
    INVENTORY_SHOW("^\\s*inventory\\s+show\\s*$"),
    INVENTORY_TRASH("^\\s*inventory\\s+trash\\s+-i\\s+(?<itemName>.+?)(\\s+-n\\s+(?<number>.+?))?\\s*$");
    private final String pattern;

    InventoryCommands(String pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) return matcher;
        return null;
    }
}
