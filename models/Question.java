package com.untildawn.models;

public class Question {
    String question;
    String answer;

    public Question() {}

    public Question(String q, String a) {
        this.question = q;
        this.answer = a;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
