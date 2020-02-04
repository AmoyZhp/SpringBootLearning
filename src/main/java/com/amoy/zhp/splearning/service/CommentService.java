package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.exception.CustomizeErrorCode;
import com.amoy.zhp.splearning.exception.CustomizeException;
import com.amoy.zhp.splearning.mapper.CommentMapper;
import com.amoy.zhp.splearning.mapper.QuestionMapper;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.Comment;
import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;


    public void insertComment(Comment comment) {
        if(comment.getCommentorId() == null || comment.getCommentorId() < 0){
            throw new CustomizeException(CustomizeErrorCode.COMMENTOR_NOT_EXIST);
        }
        if(comment.getQuestionId() == null || comment.getQuestionId() < 0){
            System.out.println("in comment service : question id is null");
            throw new CustomizeException(CustomizeErrorCode.DATA_ERROR);
        }
        User commentor = userMapper.selectByPrimaryKey(comment.getCommentorId());
        if(commentor == null){
            System.out.println("in comment service : commentorId is wrong : " + comment.getCommentorId());
            throw new CustomizeException(CustomizeErrorCode.DATA_ERROR);
        }

        Question question = questionMapper.selectByPrimaryKey(comment.getQuestionId());
        if(question == null){
            System.out.println("in comment service : questionId is wrong");
            throw new CustomizeException(CustomizeErrorCode.DATA_ERROR);
        }
        // comment to question
        if(comment.getType() == 0){
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionMapper.increaseCommentCount(question);
        } else if(comment.getType() == 1){
            // comment to comment
            if(comment.getCommentToId() != -1){
                User user = userMapper.selectByPrimaryKey(comment.getCommentorId());
                if (user != null) {
                    commentMapper.insert(comment);
                    question.setCommentCount(1);
                    questionMapper.increaseCommentCount(question);
                } else {
                    System.out.println("in comment service : commentToId is wrong");
                    throw new CustomizeException(CustomizeErrorCode.DATA_ERROR);
                }
            } else {
                System.out.println("in comment service : commentToId is null");
                throw new CustomizeException(CustomizeErrorCode.DATA_ERROR);
            }
        } else {
            System.out.println("in comment service : comment type is wrong");
            throw new CustomizeException(CustomizeErrorCode.DATA_ERROR);
        }
    }
}
