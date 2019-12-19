package com.amoy.zhp.splearning.provider;

import com.alibaba.fastjson.JSON;
import com.amoy.zhp.splearning.dto.AccessTokenDto;
import com.amoy.zhp.splearning.dto.GithubUserDto;
import okhttp3.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.stereotype.Component;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto access_token_dto){
        MediaType media_type = MediaType.get("application/json; charset=utf-8");
        String url = "https://github.com/login/oauth/access_token";

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(media_type, JSON.toJSONString(access_token_dto));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String reponse_body_string = response.body().string();
            String access_token = reponse_body_string.split("&")[0].split("=")[1];
            return access_token;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public GithubUserDto getGithubUser(String access_token){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/user?access_token="+access_token;
        Request request = new Request.Builder()
                .url(url)
                .build();
        GithubUserDto github_user = new GithubUserDto();
        try (Response response = client.newCall(request).execute()) {
            String user_str = response.body().string();
            System.out.println("user string is " + user_str);
            github_user = JSON.parseObject(user_str, GithubUserDto.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return github_user;

    }
}
