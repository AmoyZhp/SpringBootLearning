package com.amoy.zhp.splearning.dto;

import com.amoy.zhp.splearning.model.User;
import lombok.Data;

@Data
public class QuestionDto {
    private int id;
    private String title;
    private String description;
    private int creator_id;
    private int comment_count;
    private int view_count;
    private int like_count;
    private long gmt_created;
    private long gmt_modified;
    private User user;
}
