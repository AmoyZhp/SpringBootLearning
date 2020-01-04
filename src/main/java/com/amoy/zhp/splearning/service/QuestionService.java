package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.dto.QuestionDto;
import com.amoy.zhp.splearning.mapper.QuestionMapper;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private  UserMapper userMapper;

    public List<QuestionDto> listQuestions(){
        List<QuestionDto> questionDtoList = new ArrayList<>();
        List<Question> questionList = questionMapper.listQuestion();
        for(Question question : questionList){
            int user_id = question.getCreator_id();
            User user = userMapper.gerUserById(user_id);
            QuestionDto question_dto = new QuestionDto();
            BeanUtils.copyProperties(question, question_dto);
            question_dto.setUser(user);
            questionDtoList.add(question_dto);
        }
        return questionDtoList;
    }
}
