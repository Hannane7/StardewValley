package com.untildawn.Enums.GameConsts;

/*
    We have just 4 seasons in the game.
 */
public enum Seasons {

    SPRING(0, "spring"),
    SUMMER(1, "summer"),
    FALL(2, "fall"),
    WINTER(3, "winter"),
    ;


    private final int numberOfSeason;
    private final String season;

    Seasons(int numberOfSeason, String season) {
        this.numberOfSeason = numberOfSeason;
        this.season = season;
    }

    public int getNumberOfSeason() {
        return numberOfSeason;
    }

    public static Seasons getSeasonByNumber(int numberOfSeason) {
        for (Seasons season : Seasons.values()) {
            if (season.numberOfSeason == numberOfSeason) {
                return season;
            }
        }
        return null;
    }

    public String getSeason() {
        return season;
    }
}
