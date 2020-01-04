package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, creator_id, gmt_created, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{creator_id}, #{gmt_created}, #{gmt_modified})")
    void insertQuestion(Question question);

    @Select("SELECT * FROM QUESTION")
    List<Question> listQuestion();
}
