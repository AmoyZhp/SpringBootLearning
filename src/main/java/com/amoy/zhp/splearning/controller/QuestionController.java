package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.QuestionDto;
import com.amoy.zhp.splearning.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{questionId}")
    public String question(@PathVariable(name = "questionId") int questionId,
                           Model model){
        QuestionDto questionDto = questionService.getQuestionById(questionId);
        if(questionDto != null){
            model.addAttribute("question", questionDto);
            return "question";
        } else {
            return "redirect:/";
        }
    }
}
