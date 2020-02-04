package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.CommentDTO;
import com.amoy.zhp.splearning.dto.ReturnResultDTO;
import com.amoy.zhp.splearning.exception.CustomizeErrorCode;
import com.amoy.zhp.splearning.model.Comment;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.service.CommentService;
import com.amoy.zhp.splearning.service.QuestionService;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/question/comment", method = RequestMethod.POST)
    public Object addCommentToQuestion(@RequestBody CommentDTO commentDTO,
                                       HttpServletRequest request){
        Object userObj = request.getSession().getAttribute("user");
        ReturnResultDTO returnResultDTO;
        if(userObj != null){
            User user = (User) userObj;
            Comment comment = new Comment();
            comment.setQuestionId(commentDTO.getQuestionId());
            comment.setCommentorId(user.getId());
            comment.setContent(commentDTO.getContent());
            comment.setType(commentDTO.getType());
            comment.setCommentToId(commentDTO.getCommentToId());
            comment.setGmtCreated(System.currentTimeMillis());
            comment.setGmtModified(System.currentTimeMillis());
            comment.setLikeCount(0);
            commentService.insertComment(comment);
            returnResultDTO = new ReturnResultDTO(200,"success");
        } else {
            returnResultDTO = ReturnResultDTO.getResult(CustomizeErrorCode.NOT_LOGIN);
        }
        return returnResultDTO;
    }
}
