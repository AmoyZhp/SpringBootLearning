package com.amoy.zhp.splearning.exception;

public enum CustomizeErrorCode implements ICustomizeCode {
    QUESTION_NOT_FOUND(2001,"Question is not found"),
    COMMENTOR_NOT_EXIST(2002,"Commentor is not exist"),
    NOT_LOGIN(2003,"Please Login"),
    SYS_ERROR(4000,"System error"),
    DATA_ERROR(4001,"data error");
    private String message;
    private int code;

    CustomizeErrorCode(int code, String message){
        this.message = message;
        this.code = code;
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
