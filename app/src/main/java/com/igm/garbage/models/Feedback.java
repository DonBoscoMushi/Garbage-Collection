package com.igm.garbage.models;

public class Feedback {

    private String message;

    public Feedback(String message) {
        this.message = message;
    }

    public Feedback() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
