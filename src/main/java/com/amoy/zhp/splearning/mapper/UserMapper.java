package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (account_id, name, token, gmt_created, gmt_modified) VALUES (#{account_id}, #{name}, #{token}, #{gmt_created}, #{gmt_modified})")
    void inserUser(User user);
}
