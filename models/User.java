package com.untildawn.models;

import com.badlogic.gdx.graphics.Texture;
import com.untildawn.Enums.GameConsts.Gender;
import com.untildawn.controllers.utils.SHA256Hasher;


import java.util.ArrayList;

/*
    Each user is an object which have its own name, username, email, and many other fields.
 */
public class User {
    private String nickname;
    private String username;
    private String passwordHash;
    private String email;
    private Gender gender;
    private ArrayList<GameHistory> gameHistory;
    private Question securityQuestion;
    private boolean isInAnyGame;
    private Texture avatar;

    public User() {}

    public User(String name, String username, String passwordHash, String email, Gender gender, Question userSecurityQuestion) {
        this.nickname = name;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.gender = gender;
        this.gameHistory = new ArrayList<>();
        this.securityQuestion = userSecurityQuestion;
        this.isInAnyGame = false;
    }

    public ArrayList<GameHistory> getGameHistory() {
        return gameHistory;
    }

    public void setGameHistory(ArrayList<GameHistory> gameHistory) {
        this.gameHistory = gameHistory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return nickname;
    }

    public void setName(String name) {
        this.nickname = name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Gender getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSecurityQuestion(Question securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public Question getSecurityQuestion() {
        return securityQuestion;
    }
    public boolean isInAnyGame() {
        return isInAnyGame;
    }

    public boolean verifyPassword(String inputPassword) {
        String inputHash = SHA256Hasher.hashPassword(inputPassword);
        return this.passwordHash.equals(inputHash);
    }

    public void setInAnyGame(boolean inAnyGame) {
        isInAnyGame = inAnyGame;
    }

    public Texture getAvatar() {
        return avatar;
    }

    public void setAvatar(Texture avatar) {
        this.avatar = avatar;
    }
}
