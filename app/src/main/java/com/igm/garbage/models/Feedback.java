package com.igm.garbage.models;

public class Feedback {

    private String message;
    private String email;

    public Feedback(String message, String email) {
        this.message = message;
        this.email = email;
    }

    public Feedback() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
