package com.team.green.models;

import java.util.Date;

public class Request {
    private String Subscription;
    private String location;
    private Date time;
    private String UserId;

    public Request(String subscription, String location, Date time, String userId) {
        Subscription = subscription;
        this.location = location;
        this.time = time;
        UserId = userId;
    }

    public String getSubscription() {
        return Subscription;
    }

    public void setSubscription(String subscription) {
        Subscription = subscription;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "Subscription='" + Subscription + '\'' +
                ", location='" + location + '\'' +
                ", time=" + time +
                ", UserId='" + UserId + '\'' +
                '}';
    }
}

