package com.amoy.zhp.splearning.mapper;

import com.amoy.zhp.splearning.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("INSERT INTO USER (account_id, name, token, gmt_created, gmt_modified, avatar_url) VALUES (#{accountId}, #{name}, #{token}, #{gmtCreated}, #{gmtModified},#{avatarUrl})")
    void inserUser(User user);


    @Update("UPDATE USER SET token = #{token} , gmt_modified = #{gmtModified} WHERE id = #{userId}")
    void setUserTokenById(String token,int userId, long gmtModified);

    @Select("SELECT * FROM USER WHERE account_id = #{accountId}")
    User getUserByAccountId(@Param("accountId") long accountId);

    @Select("SELECT * FROM USER WHERE token = #{token}")
    User getUserByToken(@Param("token") String token);

    @Select("SELECT * FROM USER WHERE id = #{userId}")
    User getUserById(@Param("userId") int userId);

    @Update("UPDATE USER SET token = #{token}, gmt_modified = #{gmtModified}, name = #{name}, " +
            "avatar_url = #{avatarUrl}, account_id = #{accountId} " +
            "WHERE id = #{id}")
    void setUser(User user);
}
