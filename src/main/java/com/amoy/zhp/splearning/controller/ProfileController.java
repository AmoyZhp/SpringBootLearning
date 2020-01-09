package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.PagesInfoDTO;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/question")
    public String profileQuestion(HttpServletRequest request,
                                  Model model,
                                  @RequestParam(value = "page", defaultValue = "1") int page){
        Object userObj = request.getSession().getAttribute("user");
        User user;
        if(userObj != null){
            user = (User)userObj;
        } else {
            return "redirect:/";
        }
        PagesInfoDTO pagesInfoDTO = questionService.listPage(page, 5, user.getId());
        model.addAttribute("pagination",pagesInfoDTO);
        return "profile_question";
    }

}
