package com.untildawn.models;

public class GameHistory {
    private int score;
    private int coinsEarned;

    public GameHistory() {}

    public GameHistory(Game game, int score) {
        this.score = score;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }
}
