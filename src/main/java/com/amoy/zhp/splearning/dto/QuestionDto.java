package com.amoy.zhp.splearning.dto;

import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
import lombok.Data;
import org.springframework.beans.BeanUtils;

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

    public void setQuestion(Question question){
        BeanUtils.copyProperties(question, this);
    }
}
