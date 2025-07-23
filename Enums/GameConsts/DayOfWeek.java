package com.untildawn.Enums.GameConsts;

public enum DayOfWeek {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    private final int numberOfDayInWeek;

    DayOfWeek(int numberOfDayInWeek) {
        this.numberOfDayInWeek = numberOfDayInWeek;
    }

    public int getNumberOfDayInWeek() {
        return numberOfDayInWeek;
    }

    public static DayOfWeek getDayOfWeekByNumber(int numberOfDayInWeek) {
        for (DayOfWeek day : DayOfWeek.values()) {
            if (day.numberOfDayInWeek == numberOfDayInWeek) {
                return day;
            }
        }
        return null;
    }
}
