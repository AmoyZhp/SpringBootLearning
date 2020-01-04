package com.amoy.zhp.splearning.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreated;
    private Long gmtModified;
    private String avatarUrl;
}
