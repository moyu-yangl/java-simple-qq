package com.sun.domain;

import java.io.Serializable;

public class User implements Serializable {
    private String password;
    private String nickName;
    private String id;

    public User() {
    }

    public User(String password, String nickName, String id) {
        this.password = password;
        this.nickName = nickName;
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
