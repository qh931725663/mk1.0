package com.haaa.cloudmedical.common.entity;

public class RetData<T> {

    private boolean flag;

    private T       data;

    private String  error;

    public RetData() {
    }

    public RetData(boolean flag) {
        this.flag = flag;
    }

    public RetData(boolean flag, String error) {
        this.flag = flag;
        this.error = error;
    }

    public RetData(boolean flag, T data) {
        this.flag = flag;
        this.data = data;
    }

    public RetData(boolean flag, T data, String error) {
        this.flag = flag;
        this.data = data;
        this.error = error;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
