package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (account_id, name, token, gmt_created, gmt_modified, avatar_url) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreated}, #{gmtModified},#{avatarUrl})")
    void inserUser(User user);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User getUserByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE id = #{userId}")
    User gerUserById(@Param("userId") int userId);

}
