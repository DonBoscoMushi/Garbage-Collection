package com.team.green.models;

import java.util.Date;

public class Subscription {

    private String userId;
    private Date startDate;
    private Date endDate;
    private String disabled;
    private String location;
    private String subscription;

    public Subscription() {
    }

    public Subscription(String userId, Date startDate, Date endDate, String disabled, String location, String subscription) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.disabled = disabled;
        this.location = location;
        this.subscription = subscription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDisabled() {
        return disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
