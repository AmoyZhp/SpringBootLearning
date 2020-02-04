package com.amoy.zhp.splearning.exception;

public enum CustomizeSuccessCode implements ICustomizeCode{
    LOGIN_SUCCESS(2001,"login success");
    private int code;
    private String message;
    CustomizeSuccessCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getCode(){
        return this.code;
    }
}
