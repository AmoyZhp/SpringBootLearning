package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.dto.GithubUserDto;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int userId){
        return userMapper.getUserById(userId);
    }

    public User getUserByAccountId(int accountId){
        return userMapper.getUserByAccountId(accountId);
    }

    public void createUser(User user){
        userMapper.inserUser(user);
    }

    public void updateUser(User user){
        if(userMapper.getUserById(user.getId()) != null){
            userMapper.setUser(user);
        }
    }

    public User createUserByGithubUser(GithubUserDto githubUser) {
        User user = userMapper.getUserByAccountId(githubUser.getId());
        String token = UUID.randomUUID().toString();
        if( user == null){
            user = new User();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(token);
            user.setGmtCreated(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreated());
            user.setName("test");
            System.out.println("github user avatar url is " + githubUser.getAvatarUrl());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.inserUser(user);
            return user;
        } else {
            return null;
        }
    }

    public User updateUserByGithubUser(GithubUserDto githubUser) {
        User user = userMapper.getUserByAccountId(githubUser.getId());
        String token = UUID.randomUUID().toString();
        if( user != null){
            user.setToken(token);
            user.setGmtModified(user.getGmtCreated());
            userMapper.setUserTokenById(user.getToken(),user.getId(),user.getGmtModified());
            return user;
        } else {
            return null;
        }
    }
}
