package com.untildawn.Enums.GameConsts;

/*
    We have only 4 possible weather states:
 */
public enum WeatherStates {
    SUNNY(1),
    RAIN(2),
    STORM(3),
    SNOWY(4);
    private final int value;

    WeatherStates(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static WeatherStates getWeatherByValue(int value) {
        for (WeatherStates weatherStates : WeatherStates.values()) {
            if (weatherStates.getValue() == value) return weatherStates;
        }
        return null;
    }
}
