package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.dto.PagesInfoDTO;
import com.amoy.zhp.splearning.dto.QuestionDto;
import com.amoy.zhp.splearning.mapper.QuestionMapper;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
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

    public PagesInfoDTO listPage(int page, int size){

        int questionsCount = questionMapper.count();
        int totalPagesCount = questionsCount % size == 0 ? questionsCount / size : questionsCount / size + 1;
        if(page <= 0){
            page = 1;
        }
        if(page > totalPagesCount){
            page = totalPagesCount;
        }
        int offset = size * (page - 1);
        // stablish questions
        List<QuestionDto> questionDtoList = listQuestions(offset, size);
        // establish pages number
        List<Integer> pagesList = new ArrayList<>();
        for(int i = page - 2;  i <= page + 2; i++){
            if(i <= 0){
                continue;
            }
            if(i > totalPagesCount){
                break;
            }
            pagesList.add(i);
        }
        PagesInfoDTO pageInfoDTO = new PagesInfoDTO();
        pageInfoDTO.setCurrentPage(page);
        pageInfoDTO.setTotalPagesCount(totalPagesCount);
        pageInfoDTO.setPages(pagesList);
        pageInfoDTO.setQuestionList(questionDtoList);
        return pageInfoDTO;
    }
    public List<QuestionDto> listQuestions(int offset, int size){
        List<QuestionDto> questionDtoList = new ArrayList<>();
        List<Question> questionList = questionMapper.listQuestion(offset, size);
        for(Question question : questionList){
            int userId = question.getCreatorId();
            User user = userMapper.gerUserById(userId);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
//    public List<QuestionDto> listQuestions(){
//        List<QuestionDto> questionDtoList = new ArrayList<>();
//        List<Question> questionList = questionMapper.listQuestion();
//        for(Question question : questionList){
//            int userId = question.getCreatorId();
//            User user = userMapper.gerUserById(userId);
//            QuestionDto questionDto = new QuestionDto();
//            BeanUtils.copyProperties(question, questionDto);
//            questionDto.setUser(user);
//            questionDtoList.add(questionDto);
//        }
//        return questionDtoList;
//    }
}
