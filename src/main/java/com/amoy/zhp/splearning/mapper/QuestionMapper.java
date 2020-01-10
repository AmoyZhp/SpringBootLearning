package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO QUESTION (title, description, creator_id, gmt_created, gmt_modified) " +
            "VALUES (#{title}, #{description}, #{creatorId}, #{gmtCreated}, #{gmtModified})")
    void insertQuestion(Question question);


    @Update("UPDATE QUESTION SET title = #{title}, description = #{description}, gmt_modified = #{gmtModified} " +
            "WHERE id = #{id} ")
    void updateQuestion(Question question);

    @Select("SELECT * FROM QUESTION")
    List<Question> listAllQuestion();

    @Select("SELECT * FROM QUESTION LIMIT #{offset}, #{size}")
    List<Question> listQuestion(int offset, int size);

    @Select("SELECT * FROM QUESTION WHERE CREATOR_ID = #{userId} LIMIT #{offset}, #{size} ")
    List<Question> listQuestionByUserId(int offset, int size, int userId);

    @Select("SELECT COUNT(1) FROM QUESTION")
    int count();

    @Select("SELECT COUNT(1) FROM QUESTION WHERE CREATOR_ID = #{userId}")
    int countByUserId(int userId);

    @Select("SELECT * FROM QUESTION WHERE id = #{questionId}")
    Question getQuestionById(@Param("questionId") int questionId);
}
