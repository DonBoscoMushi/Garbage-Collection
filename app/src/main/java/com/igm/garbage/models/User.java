package com.igm.garbage.models;

public class User {

    private String user_id;
    private String fullname;
    private String phone_no;
    private String email;
    private String role;

    public User() { }

    public User(String fullname, String phone_no, String email, String role) {
        this.fullname = fullname;
        this.phone_no = phone_no;
        this.email = email;
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
