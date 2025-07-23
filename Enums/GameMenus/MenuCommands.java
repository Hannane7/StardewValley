package com.untildawn.Enums.GameMenus;

import java.util.regex.Matcher;

public interface MenuCommands {
    Matcher getMatcher(String input);
}
