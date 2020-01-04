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
        accessTokenDto.setClient_id(githubClientId);
        accessTokenDto.setClient_secret(githubClientSecret);
        accessTokenDto.setCode(code);
        accessTokenDto.setRedirect_uri(githubUri);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUserDto github_user = githubProvider.getGithubUser(accessToken);
        if(github_user != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccount_id(String.valueOf(github_user.getId()));
            user.setToken(token);
            user.setGmt_created(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_created());
            user.setName("test");
            System.out.println("github user avatar url is " + github_user.getAvatar_url());
            user.setAvatar_url(github_user.getAvatar_url());
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
