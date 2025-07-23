package com.untildawn.controllers.utils;

import java.security.SecureRandom;
import java.util.*;
public class PasswordGenerator {
    private static final String SPECIAL_CHARS = "?><,\"';:\\/|][}{+=)(*&^%$#!";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(String username) {
        StringBuilder usernameBuilder = new StringBuilder();
        for (char c : username.toCharArray()) {
            if (Character.isLetter(c)) {
                usernameBuilder.append(random.nextBoolean() ? Character.toLowerCase(c) : Character.toUpperCase(c));
            } else {
                usernameBuilder.append(c);
            }
        }

        String randomizedUsername = usernameBuilder.toString();

        char specialChar = SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));

        char numberChar = NUMBERS.charAt(random.nextInt(NUMBERS.length()));

        char lowercaseChar = (char) ('a' + random.nextInt(26));
        char uppercaseChar = (char) ('A' + random.nextInt(26));

        List<Character> passwordChars = new ArrayList<>();
        for (char c : randomizedUsername.toCharArray()) {
            passwordChars.add(c);
        }

        passwordChars.add(specialChar);
        passwordChars.add(numberChar);
        passwordChars.add(lowercaseChar);
        passwordChars.add(uppercaseChar);

        while (passwordChars.size() < 10) {
            int type = random.nextInt(4);
            char c;
            if (type == 0) c = (char) ('a' + random.nextInt(26));
            else if (type == 1) c = (char) ('A' + random.nextInt(26));
            else if (type == 2) c = NUMBERS.charAt(random.nextInt(NUMBERS.length()));
            else c = SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length()));
            passwordChars.add(c);
        }

        Collections.shuffle(passwordChars, random);

        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
