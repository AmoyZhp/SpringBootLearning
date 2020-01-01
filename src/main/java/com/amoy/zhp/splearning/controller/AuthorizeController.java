package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.AccessTokenDto;
import com.amoy.zhp.splearning.dto.GithubUserDto;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider gihub_provider;

    @Value("${github.client_id}")
    private String github_client_id;
    @Value("${github.client_secret}")
    private String github_client_secret;
    @Value("${github.redirect_uri}")
    private String github_uri;

    @Autowired
    private UserMapper user_mapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDto access_token_dto = new AccessTokenDto();
        access_token_dto.setClient_id(github_client_id);
        access_token_dto.setClient_secret(github_client_secret);
        access_token_dto.setCode(code);
        access_token_dto.setRedirect_uri(github_uri);
        access_token_dto.setState(state);
        String access_token = gihub_provider.getAccessToken(access_token_dto);
        GithubUserDto github_user = gihub_provider.getGithubUser(access_token);
        if(github_user != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccount_id(String.valueOf(github_user.getId()));
            user.setToken(token);
            user.setGmt_created(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_created());
            user.setName("test");
            user_mapper.inserUser(user);
            Cookie cookie = new Cookie("token", token);
            System.out.println(cookie.getPath());
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
