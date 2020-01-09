package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (account_id, name, token, gmt_created, gmt_modified, avatar_url) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreated}, #{gmtModified},#{avatarUrl})")
    void inserUser(User user);


    @Update("UPDATE USER SET token = #{token} WHERE id = #{userId}")
    void setUserTokenById(String token,int userId);

    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User getUserByAccountId(@Param("accountId") long accountId);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User getUserByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE id = #{userId}")
    User gerUserById(@Param("userId") int userId);

}
