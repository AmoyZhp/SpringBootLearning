package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.dto.PagesInfoDTO;
import com.amoy.zhp.splearning.dto.QuestionDto;
import com.amoy.zhp.splearning.mapper.QuestionMapper;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.QuestionExample;
import com.amoy.zhp.splearning.model.User;
import org.apache.ibatis.session.RowBounds;
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


    public PagesInfoDTO listPage(int page, int size, int userId){
        return this.listPageImpl(page, size, userId);
    }

    public PagesInfoDTO listPage(int page, int size){

        QuestionExample questionExample = new QuestionExample();
        int questionsCount = (int) questionMapper.countByExample(questionExample);
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
        RowBounds rowBounds = new RowBounds(offset, size);
        QuestionExample questionExample = new QuestionExample();
        List<Question> questionList = questionMapper.selectByExampleWithRowbounds(questionExample, rowBounds);
        for(Question question : questionList){
            int userId = question.getCreatorId();
            User user = userMapper.selectByPrimaryKey(userId);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }

    private PagesInfoDTO listPageImpl(int page, int size, int userId){
        PagesInfoDTO pageInfoDTO = new PagesInfoDTO();
        int questionsCount;
        QuestionExample questionExample = new QuestionExample();
        if(userId != -1){
            questionExample.createCriteria().andCreatorIdEqualTo(userId);
        }
        questionsCount = (int)questionMapper.countByExample(questionExample);
        int totalPagesCount = questionsCount % size == 0 ? questionsCount / size : questionsCount / size + 1;
        if(page <= 0){
            page = 1;
        }
        if(page > totalPagesCount){
            page = totalPagesCount;
        }
        int offset = size * (page - 1);

        List<QuestionDto> questionDtoList = new ArrayList<>();
        List<Question> questionList;
        RowBounds rowBounds = new RowBounds(offset, size);
        questionExample = new QuestionExample();

        if(userId != -1){
            questionExample.createCriteria().andCreatorIdEqualTo(userId);
        }
        questionList = questionMapper.selectByExampleWithRowbounds(questionExample, rowBounds);
        for(Question question : questionList){
            int creatorId = question.getCreatorId();
            User user = userMapper.selectByPrimaryKey(creatorId);
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }

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

        pageInfoDTO.setCurrentPage(page);
        pageInfoDTO.setTotalPagesCount(totalPagesCount);
        pageInfoDTO.setPages(pagesList);
        pageInfoDTO.setQuestionList(questionDtoList);

        return pageInfoDTO;
    }

    public QuestionDto getQuestionDtoById(int questionId) {
        QuestionDto questionDto = new QuestionDto();
        Question question = questionMapper.selectByPrimaryKey(questionId);
        questionDto.setQuestion(question);
        User user = userMapper.selectByPrimaryKey(question.getCreatorId());
        if(user != null){
            questionDto.setUser(user);
        }
        return questionDto;
    }


    public void createQuestion(Question question) {
        questionMapper.insert(question);
    }

    public void updateQuestion(Question question) {
        questionMapper.updateByPrimaryKeySelective(question);
    }
}
