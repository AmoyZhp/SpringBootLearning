package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, creator_id, gmt_created, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{creator_id}, #{gmt_created}, #{gmt_modified})")
    public void insertQuestion(Question question);
}
