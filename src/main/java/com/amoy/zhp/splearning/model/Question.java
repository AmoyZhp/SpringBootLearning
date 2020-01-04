package com.amoy.zhp.splearning.model;

import lombok.Data;

@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private int creatorId;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private long gmtCreated;
    private long gmtModified;
}
