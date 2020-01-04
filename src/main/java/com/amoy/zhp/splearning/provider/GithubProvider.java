package com.amoy.zhp.splearning.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.amoy.zhp.splearning.dto.AccessTokenDto;
import com.amoy.zhp.splearning.dto.GithubUserDto;
import okhttp3.*;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.stereotype.Component;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDto accessTokenDto){
        MediaType media_type = MediaType.get("application/json; charset=utf-8");
        String url = "https://github.com/login/oauth/access_token";

        SerializeConfig config = new SerializeConfig(); // 生产环境中，config要做singleton处理，要不然会存在性能问题
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;

        OkHttpClient client = new OkHttpClient();
        String accessToeknJsonStr = JSON.toJSONString(accessTokenDto, config);
        System.out.println("access token json string " + accessToeknJsonStr);
        RequestBody body = RequestBody.create(media_type, accessToeknJsonStr);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String reponseBodyString = response.body().string();
            String accessToken = reponseBodyString.split("&")[0].split("=")[1];
            return accessToken;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public GithubUserDto getGithubUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.github.com/user?access_token=" + accessToken;
        Request request = new Request.Builder()
                .url(url)
                .build();
        GithubUserDto github_user = new GithubUserDto();
        try (Response response = client.newCall(request).execute()) {
            String userStr = response.body().string();
            System.out.println("user string is " + userStr);
            github_user = JSON.parseObject(userStr, GithubUserDto.class);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return github_user;

    }
}
