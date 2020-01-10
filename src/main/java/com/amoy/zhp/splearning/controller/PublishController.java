package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.QuestionDto;
import com.amoy.zhp.splearning.mapper.QuestionMapper;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private UserMapper user_mapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String getPublish(){
        return "publish";
    }

    @GetMapping("/publish/{questionId}")
    public String edit(@PathVariable(name = "questionId", required = false) int questionId,
                       HttpServletRequest request,
                       Model model){
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        Object userObj = request.getSession().getAttribute("user");
        if(userObj != null){
            User user = (User) userObj;
            if(user.getId() == questionDto.getCreatorId()){
                model.addAttribute("title", questionDto.getTitle());
                model.addAttribute("description", questionDto.getDescription());
                model.addAttribute("questionId",questionId);
                return "publish";
            }
        }
        return "redirect:/";
    }

    @PostMapping("/publish")
    public String publish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "questionId", required = false, defaultValue = "-1") int questionId,
            HttpServletRequest request,
            Model model){
        System.out.println("title is " + title);
        if(title == null || title == ""){
            model.addAttribute("error","title should not be empty");
            return "publish";
        }
        model.addAttribute("title", title);
        if(description == null || description.equals("")){
            model.addAttribute("error","description should not be empty");
            return "publish";
        }
        model.addAttribute("description", description);

        Object userObj = request.getSession().getAttribute("user");
        if( userObj == null){
            model.addAttribute("error", " please log in first");
            return "publish";
        } else {
            User user = (User)userObj;
            System.out.println("quesion id is " + questionId);
            if(questionId != -1){
                Question question = questionMapper.getQuestionById(questionId);
                if(question.getCreatorId() == user.getId()){
                    question.setTitle(title);
                    question.setDescription(description);
                    question.setGmtModified(System.currentTimeMillis());
                    questionMapper.updateQuestion(question);
                }
            } else {
                Question question = new Question();
                question.setTitle(title);
                question.setDescription(description);
                question.setCreatorId(user.getId());
                question.setGmtCreated(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreated());
                questionMapper.insertQuestion(question);
            }
            return "redirect:/";
        }

    }
}
