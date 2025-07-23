package com.untildawn.Enums.MapConsts;

public enum AnsiColors {
    RESET("\u001B[0m", "\u001B[0m"),
    BLACK("\u001B[30m", "\u001B[40m"),
    RED("\u001B[31m", "\u001B[41m"),
    GREEN("\u001B[32m", "\u001B[42m"),
    YELLOW("\u001B[33m", "\u001B[43m"),
    BLUE("\u001B[34m", "\u001B[44m"),
    PURPLE("\u001B[35m", "\u001B[45m"),
    CYAN("\u001B[36m", "\u001B[46m"),
    WHITE("\u001B[37m", "\u001B[47m"),
    BROWN("\u001B[38;5;94m", "\u001B[48;5;94m"),
    ORANGE("\u001B[38;5;208m", "\u001B[48;5;208m"),
    PINK("\u001B[38;5;200m", "\u001B[48;5;200m"),
    GREY("\u001B[38;5;240m", "\u001B[48;5;240m"),
    LIGHT_GREEN("\u001B[38;5;119m", "\u001B[48;5;119m"),
    LIGHT_BLUE("\u001B[38;5;117m", "\u001B[48;5;117m"),
    ;
    private final String foregroundCode;
    private final String backgroundCode;

    AnsiColors(String foregroundCode, String backgroundCode) {
        this.foregroundCode = foregroundCode;
        this.backgroundCode = backgroundCode;
    }

    public String wrap(String text) {
        return foregroundCode + text + RESET.foregroundCode;
    }

    public String wrapBackground(String text) {
        return backgroundCode + text + RESET.backgroundCode;
    }

    public static String wrap(String text, AnsiColors fg, AnsiColors bg) {
        return fg.foregroundCode + bg.backgroundCode + text + RESET.foregroundCode + RESET.backgroundCode;
    }

    public String getForegroundCode() {
        return foregroundCode;
    }

    public String getBackgroundCode() {
        return backgroundCode;
    }
}
