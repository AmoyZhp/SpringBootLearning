package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.PagesInfoDTO;
import com.amoy.zhp.splearning.dto.QuestionDto;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        Cookie[] cookies = request.getCookies();
        if( cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    User user = userMapper.getUserByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("github_user",user);
                    }
                    break;
                }
            }
        }
        PagesInfoDTO pagesInfoDTO = questionService.listPage(page, 5);
//        List<QuestionDto> questionDtoList = new ArrayList<>();
//        questionDtoList = questionService.listQuestions();
        model.addAttribute("pagination",pagesInfoDTO);
        return "index";
    }
}
