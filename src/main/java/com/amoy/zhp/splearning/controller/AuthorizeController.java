package com.amoy.zhp.splearning.controller;

import com.amoy.zhp.splearning.dto.AccessTokenDto;
import com.amoy.zhp.splearning.dto.GithubUserDto;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.provider.GithubProvider;
import com.amoy.zhp.splearning.service.UserService;
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
    private UserService userService;

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
            User user = userService.createUserByGithubUser(githubUser);
            // if user == null means that there is a github user exist
            if( user == null){
                user = userService.updateUserByGithubUser(githubUser);
            }
            Cookie cookie = new Cookie("token", user.getToken());
            cookie.setMaxAge(60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Object userObj = request.getSession().getAttribute("user");
        if(userObj != null){
            User user = (User) userObj;
            user.setToken("");
            user.setGmtModified(System.currentTimeMillis());
            userService.updateUser(user);
            request.getSession().removeAttribute("user");
            Cookie cookie = new Cookie("token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        return "redirect:/";
    }
}
