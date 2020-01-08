package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, creator_id, gmt_created, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{creatorId}, #{gmtCreated}, #{gmtModified})")
    void insertQuestion(Question question);

//    @Select("SELECT * FROM QUESTION")
//    List<Question> listQuestion();

    @Select("SELECT * FROM QUESTION LIMIT #{offset}, #{size}")
    List<Question> listQuestion(int offset, int size);

    @Select("SELECT COUNT(1) FROM QUESTION")
    int count();
}
