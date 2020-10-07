package com.team.green.models;

public class Request {
    private String location;
    private String phone;

    public Request(String location, String phone) {
        this.location = location;
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

