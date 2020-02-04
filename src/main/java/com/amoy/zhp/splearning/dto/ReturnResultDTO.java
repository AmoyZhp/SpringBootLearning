package com.amoy.zhp.splearning.dto;

import com.amoy.zhp.splearning.exception.CustomizeErrorCode;
import com.amoy.zhp.splearning.exception.CustomizeException;
import com.amoy.zhp.splearning.exception.CustomizeSuccessCode;
import com.amoy.zhp.splearning.exception.ICustomizeCode;

public class ReturnResultDTO {
    private String message;
    private int code;

    public ReturnResultDTO(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static ReturnResultDTO getResult(CustomizeErrorCode errorCode){
        ReturnResultDTO returnResultDTO = new ReturnResultDTO(errorCode.getCode(), errorCode.getMessage());
        return  returnResultDTO;
    }

    public static ReturnResultDTO getResult(CustomizeException e){
        ReturnResultDTO returnResultDTO = new ReturnResultDTO(e.getCode(), e.getMessage());
        return  returnResultDTO;
    }

    public static ReturnResultDTO getResult(CustomizeSuccessCode e){
        ReturnResultDTO returnResultDTO = new ReturnResultDTO(e.getCode(), e.getMessage());
        return  returnResultDTO;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
