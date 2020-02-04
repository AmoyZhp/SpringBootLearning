package com.amoy.zhp.splearning.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private long questionId;
    private String content;
    private int type;
    private long commentToId;
}
