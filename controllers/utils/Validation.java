package com.untildawn.controllers.utils;

import com.untildawn.models.App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Validation {
    public static String handleDuplicateUsername(String username, Scanner sc) {
        System.out.printf("Username is already taken.\n");
        String randomUsername = username;
        Random rand = new Random();
        while (true) {
            while (App.userExists(randomUsername)) {
                int leadingOrTrailing = rand.nextInt() % 2;
                String randomNumber = Integer.toString((int) generateRandomNumber(4));
                if (leadingOrTrailing == 0) {
                    randomUsername = username + randomNumber;
                } else {
                    randomUsername = randomNumber + username;
                }
            }
            while (true) {
                boolean generateNewUsername = false;
                System.out.printf("Suggestion: %s\n", randomUsername);
                System.out.printf("1- choose\n2- generate another username\n3- back\n");
                String command = sc.nextLine();
                switch (command) {
                    case "1", "choose" -> {
                        System.out.printf("Username %s chosen.\n", randomUsername);
                        return randomUsername;
                    }
                    case "2", "generate another username" -> {
                        generateNewUsername = true;
                    }
                    case "3", "back" -> {
                        return null;
                    }
                    default -> {
                        System.out.printf("Invalid command. please try again.\n");
                    }
                }
                if (generateNewUsername) break;
            }
        }
    }
    public static String handleRandomPassword(String username, Scanner sc) {
        while (true) {
            String randomPassword = PasswordGenerator.generatePassword(username);
            System.out.printf(randomPassword + "\n");
            System.out.printf("1- choose\n2- generate another password\n3- back\n");
            String command = sc.nextLine();
            switch (command) {
                case "1", "choose" -> {
                    System.out.printf("password chosen.");
                    return randomPassword;
                }
                case "2", "generate another password" -> {

                }
                case "3", "back" -> {
                    return null;
                }
                default -> {
                    System.out.printf("Invalid command. please try again.\n");
                }
            }
        }
    }
    public static String handleWeakPassword(String password, int state) {
        switch (state) {
            case 0:
                return "Your password is too short.\n";
            case 1:
                return "Your password is weak. It must contain at least one special character.\n";
            case 2:
                return "Your password is weak. It must contain at least one lowercase and uppercase letters.\n";
            default: return "Register failed. please try again.\n";
        }
    }
    public static boolean isUsernameValid(String username) {
        return username.matches("^[a-zA-Z0-9-]+$");
    }
    public static boolean isEmailValid(String email) {
        String emailRegex = "^(?!.*\\.\\.)[a-zA-Z0-9](?:[a-zA-Z0-9._-]*[a-zA-Z0-9])?@(?=[a-zA-Z0-9])[a-zA-Z0-9.-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
    public static boolean isPasswordValid(String password) {
        return password.matches("^[a-zA-Z0-9?><,\"';:/\\\\|\\]\\[}{+=)(*&^%$#!]*$"
        );
    }
    public static int isPasswordWeak(String password) {
        if (password.length() < 8) return 0;
        ArrayList<Character> specialChars = new ArrayList<>(Arrays.asList('?', '>', '<', ',', '"', '\'', ';', ':', '/',
                '|', ']', '[', '}', '{', '+', '=', ')', '(', '*', '&', '^', '%', '$', '#', '!'));
        boolean containsSpecialChars = false;
        for (Character ch : specialChars) {
            if (password.indexOf(ch) != -1) {
                containsSpecialChars = true;
                break;
            }
        }
        if (!containsSpecialChars) return 1;

        boolean hasLowerCase =  false;
        boolean hasUppercase = false;
        for (Character ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) hasUppercase = true;
            if (Character.isLowerCase(ch)) hasLowerCase = true;
        }
        if (!hasUppercase || !hasLowerCase) return 2;

        return -1;
    }
    public static boolean isNicknameValid(String nickname) {
        return nickname.matches("^[a-zA-Z0-9 -_]+$");
    }
    public static long generateRandomNumber(int length) {
        Random random = new Random();
        long min = (long) Math.pow(10, length - 1);
        long max = (long) Math.pow(10, length) - 1;

        return min + ((long)(random.nextDouble() * (max - min + 1)));
    }
}
