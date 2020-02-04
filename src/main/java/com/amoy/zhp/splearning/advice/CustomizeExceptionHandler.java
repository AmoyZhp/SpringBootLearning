package com.amoy.zhp.splearning.advice;

import com.alibaba.fastjson.JSON;
import com.amoy.zhp.splearning.dto.ReturnResultDTO;
import com.amoy.zhp.splearning.exception.CustomizeErrorCode;
import com.amoy.zhp.splearning.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable ex, Model model, HttpServletResponse response){
        if(request.getContentType().equals("application/json")){
            ReturnResultDTO returnResultDTO;
            System.out.println(ex.getMessage());
            if( ex instanceof CustomizeException){
                returnResultDTO = ReturnResultDTO.getResult((CustomizeException) ex);
            } else {
                returnResultDTO = ReturnResultDTO.getResult(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(returnResultDTO));
                writer.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            return null;
        } else {
            ModelAndView modelAndView = new ModelAndView("error");
            if( ex instanceof CustomizeException){
                model.addAttribute("message", ex.getMessage());
            } else {
                model.addAttribute("message",ex.getMessage());
            }
            return modelAndView;
        }
    }
}
