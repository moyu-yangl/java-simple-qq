package com.sun.domain;

import com.sun.utils.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String sender;

    private String senderName;
    private String receiver;
    private String receiverName;
    private String msg;
    private Date date;

    public Message() {
    }

    public Message(String msg) {
        sender = "1";
        receiver = "2";
        date = new Date();
        this.msg = msg;
    }

    public Message(String sender, String receiver, String msg, String senderName) {
        this.sender = sender;
        this.receiver = receiver;
        this.msg = msg;
        this.date = new Date();
        this.senderName = senderName;
    }

    @Override
    public String toString() {
        return " " + DateUtil.getTime(date) + "  \n " + senderName + "  :\n " + msg + "\n\n";
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
