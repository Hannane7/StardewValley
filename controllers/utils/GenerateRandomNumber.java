package com.untildawn.controllers.utils;

import java.util.Random;
public class  GenerateRandomNumber {
    public static int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
    public static double generateRandomDoubleNumber(double min, double max) {
        Random random = new Random();
        return random.nextDouble() * (max - min) + min;
    }
}
