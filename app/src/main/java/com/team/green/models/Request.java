package com.team.green.models;

import java.util.Date;

public class Request {
    private String userId;
    private String status;
    private String transactionId;

    public Request() {
    }

    public Request(String userId, String status, String transactionId) {
        this.userId = userId;
        this.status = status;
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}

