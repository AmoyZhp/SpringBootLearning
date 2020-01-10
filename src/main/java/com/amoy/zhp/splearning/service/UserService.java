package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
