package com.untildawn.models;

public class Message {
    private final MessageType type;
    private final String content;
    private Runnable runnable;

    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public String getContent() {
        return content;
    }

    public MessageType getType() {
        return type;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public enum MessageType {
        ERROR,
        WARNING,
        CONFIRMATION,
        ;
    }
}
