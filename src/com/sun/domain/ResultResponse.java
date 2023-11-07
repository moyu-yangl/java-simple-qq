package com.sun.domain;

import com.sun.system.Constant;

import java.io.Serializable;

public class ResultResponse<T> implements Serializable {
    private String msg;
    private Integer code;
    private T data;

    private String path;

    public ResultResponse() {
        this.data = null;
        this.code = 200;
        this.msg = "成功";
    }

    public ResultResponse(T data) {
        this.data = data;
        this.code = 200;
        this.msg = "成功";
    }

    public ResultResponse(String msg, Integer code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
    }

    public ResultResponse(Constant constant, T data) {
        this.code = constant.getCode();
        this.msg = constant.getMsg();
        this.data = data;
    }

    public ResultResponse(Constant constant) {
        this.code = constant.getCode();
        this.msg = constant.getMsg();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ResultResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
