package com.team.green.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Subscription implements Parcelable {

    private String subscriptionId;
    private String userId;
    private Date startDate;
    private Date endDate;
    private String disabled;
    private String location;
    private String subscription;
    private String status;
    private String name;
    private String phone;

    public Subscription() {
    }

    public Subscription(String subscriptionId, String userId, Date startDate, Date endDate, String disabled, String location, String subscription, String status, String name, String phone) {
        this.subscriptionId = subscriptionId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.disabled = disabled;
        this.location = location;
        this.subscription = subscription;
        this.status = status;
        this.name = name;
        this.phone = phone;
    }

    public Subscription(String userId, Date startDate, Date endDate, String disabled, String location, String subscription, String status, String name, String phone) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.disabled = disabled;
        this.location = location;
        this.subscription = subscription;
        this.status = status;
        this.name = name;
        this.phone = phone;
    }

    protected Subscription(Parcel in) {
        subscriptionId = in.readString();
        userId = in.readString();
        disabled = in.readString();
        location = in.readString();
        subscription = in.readString();
        status = in.readString();
        name = in.readString();
        phone = in.readString();
        startDate = new Date(in.readLong());
    }

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel in) {
            return new Subscription(in);
        }

        @Override
        public Subscription[] newArray(int size) {
            return new Subscription[size];
        }
    };

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subscriptionId);
        parcel.writeString(userId);
        parcel.writeString(disabled);
        parcel.writeString(location);
        parcel.writeString(subscription);
        parcel.writeString(status);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeLong(startDate.getTime());
    }


}
