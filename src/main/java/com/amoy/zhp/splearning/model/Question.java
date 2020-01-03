package com.amoy.zhp.splearning.model;

import lombok.Data;

@Data
public class Question {
    private int id;
    private String title;
    private String description;
    private int creator_id;
    private int comment_count;
    private int view_count;
    private int like_count;
    private long gmt_created;
    private long gmt_modified;
}
