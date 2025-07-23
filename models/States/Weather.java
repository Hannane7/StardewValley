package com.untildawn.models.States;

import com.untildawn.Enums.GameConsts.WeatherStates;


/*
    A static class to store current weather state, change and return it when needed.
 */
public class Weather {
    private WeatherStates currentWeather;
    private boolean isLightning = false;


    public Weather() {
        this.currentWeather = WeatherStates.SUNNY;
    }

    public WeatherStates getCurrentWeather() {
        return currentWeather;
    }

    public void setCurrentWeather(WeatherStates weather) {
        this.currentWeather = weather;
    }

    public boolean isLightning() {
        return isLightning;
    }

    public void setLightning(boolean lightning) {
        isLightning = lightning;
    }
}
