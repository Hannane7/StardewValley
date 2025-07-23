package com.untildawn.models.MessageCenter;

import com.untildawn.views.AppMenu;

import java.util.HashMap;


public class Message {
    private MessageType type;
    private HashMap<String, Object> body;

    public Message() {}

    public Message(MessageType type, HashMap<String, Object> body) {
        this.type = type;
        this.body = body;
    }


    public MessageType getType() {
        return type;
    }

    public Object getFromBody(String key) {
        return body.get(key);
    }

    public String getContent() {
        return (String) body.get("content");
    }

    public Runnable getRunnable() {
        return (Runnable) body.get("onClose");
    }

//    public void send(AppMenu view) {
//        if (type == null) return;
//        switch (type) {
//            case SUCCESS:
//                view.showMessage(this);
//                break;
//            case ERROR:
//                view.showError(this);
//                break;
//            case WARNING:
//                view.showMessage(this);
//                break;
//            case CONFIRMATION:
//                view.showConfirmation(this);
//                break;
//        }
//    }
}
