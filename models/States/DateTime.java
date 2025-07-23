package com.untildawn.models.States;

import com.untildawn.Enums.GameConsts.DayOfWeek;
import com.untildawn.Enums.GameConsts.Seasons;



/*
    A static class to store current time, change and return it when needed.
 */
public class DateTime {
    private int day; // day in month - from 1 to 21
    private Seasons season; // current season - 4 seasons
    private int hour; // from 9 to 22
    private DayOfWeek dayOfWeek; // from Monday to Saturday


    public DateTime() {
        this.day = 1;
        this.season = Seasons.SPRING;
        this.hour = 9;
        this.dayOfWeek = DayOfWeek.MONDAY;
    }

    public void updateTimeByDay(int day) {
        int seasonPassed = (this.day + day) / 22;
        int newSeasonNumber = ((this.season.getNumberOfSeason() + seasonPassed) % 4);
        this.season = Seasons.getSeasonByNumber(newSeasonNumber);
        int newDayOfWeekNumber = ((this.dayOfWeek.getNumberOfDayInWeek() + day) % 7);
        this.dayOfWeek = DayOfWeek.getDayOfWeekByNumber(newDayOfWeekNumber);
        this.day = ((this.day + day) % 22);
        if(this.day == 0) {
            this.day = 1;
        }
        this.hour = 9;
    }

    public int updateTimeByHour(int hour) {
        int dayPassed = (this.hour - 9 + hour) / 13;
        int newHour = ((this.hour - 9 + hour) % 13) + 9;
        updateTimeByDay(dayPassed);
        this.hour = newHour;
        return newHour;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public Seasons getSeason() {
        return season;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }
}
