package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.mapper.QuestionMapper;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.Question;
import com.amoy.zhp.splearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired
    private UserMapper user_mapper;

    @Autowired
    private QuestionMapper question_mapper;

    @GetMapping("/publish")
    public String getPublish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description",required = false) String description,
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

        Cookie[] cookies = request.getCookies();
        User user = null;
        if( cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = user_mapper.find_user_by_token(token);
                    break;
                }
            }
        }
        if( user == null){
            model.addAttribute("error", " please log in first");
            return "publish";
        } else {
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setCreator_id(user.getId());
            question.setGmt_created(System.currentTimeMillis());
            question.setGmt_modified(question.getGmt_created());
            question_mapper.insertQuestion(question);
            return "redirect:/";
        }

    }
}
