package com.amoy.zhp.splearning.dto;

import lombok.Data;

@Data
public class AccessTokenDto {
    private String clientId;
    private String clientSecret;
    private String code;
    private String redirectUri;
    private String state;
}
