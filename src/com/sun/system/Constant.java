package com.sun.system;

public enum Constant {
    SUCCESS(200, "成功"),
    DEFAULT(201, "失败"),
    LOGIN(1, "登录"),
    REGISTERED(2, "id已被注册"),
    REGISTER_NEED(3, "需要注册"),
    USER_ONLINE(4, "好友已经上线"),

    USER_LOGOUT(5, "好友下线了"),

    ONLINE_USERS(6, "在线好友"),

    NEW_MESSAGE(7, "有新消息"),

    ;


    private int code;
    private String msg;

    Constant(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
