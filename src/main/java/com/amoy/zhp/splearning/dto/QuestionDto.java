package com.amoy.zhp.splearning.dto;

import com.amoy.zhp.splearning.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private int id;
    private String title;
    private String description;
    private int creatorId;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private long gmtCreated;
    private long gmtModified;
    private User user;
}
