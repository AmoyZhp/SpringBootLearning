package com.amoy.zhp.splearning.service;

import com.amoy.zhp.splearning.dto.GithubUserDto;
import com.amoy.zhp.splearning.mapper.UserMapper;
import com.amoy.zhp.splearning.model.User;
import com.amoy.zhp.splearning.model.UserExample;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getUserById(int userId){
        return userMapper.selectByPrimaryKey(userId);
    }


    public void createUser(User user){
        userMapper.insert(user);
    }

    public void updateUser(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }

    public User createUserByGithubUser(GithubUserDto githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> selectResult = userMapper.selectByExample(userExample);
        if(selectResult.size() == 0){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setToken(token);
            user.setGmtCreated(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreated());
            user.setName("test");
            System.out.println("github user avatar url is " + githubUser.getAvatarUrl());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);
            return user;
        } else {
            return null;
        }
    }


    public User updateUserByGithubUser(GithubUserDto githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> selectResult = userMapper.selectByExample(userExample);
        String token = UUID.randomUUID().toString();
        if( selectResult.size() > 0){
            User user = new User();
            user.setId(selectResult.get(0).getId());
            user.setToken(token);
            user.setGmtModified(user.getGmtCreated());
            userMapper.updateByPrimaryKeySelective(user);
            return user;
        } else {
            return null;
        }
    }

    public User getUserByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList.size() > 0){
            User user = userList.get(0);
            return user;
        } else {
            return null;
        }
    }
}
