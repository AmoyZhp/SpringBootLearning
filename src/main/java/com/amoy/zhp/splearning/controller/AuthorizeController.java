package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.AccessTokenDto;
import com.amoy.zhp.splearning.dto.GithubUserDto;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client_id}")
    private String githubClientId;
    @Value("${github.client_secret}")
    private String githubClientSecret;
    @Value("${github.redirect_uri}")
    private String githubUri;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClientId(githubClientId);
        accessTokenDto.setClientSecret(githubClientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirectUri(githubUri);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUserDto githubUser = githubProvider.getGithubUser(accessToken);
        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(token);
            user.setGmtCreated(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreated());
            user.setName("test");
            System.out.println("github user avatar url is " + githubUser.getAvatarUrl());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.inserUser(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            return "redirect:/";
        }
    }
}
