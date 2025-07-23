package com.untildawn.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserDataHandler {

    private static String filePath = "src/main/java/org/example/Data/Users.json";

    public static void loadUsers() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            ArrayList<User> users = mapper.readValue(
                    new File(filePath),
                    new TypeReference<>() {
                    }
            );

            App.setUsers(users);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void saveUsers() {
        ArrayList<User> users = new ArrayList<>(App.getUsers().values());
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), users);
        } catch (IOException e) {
            System.err.println("Error saving users.");
        }
    }
}
