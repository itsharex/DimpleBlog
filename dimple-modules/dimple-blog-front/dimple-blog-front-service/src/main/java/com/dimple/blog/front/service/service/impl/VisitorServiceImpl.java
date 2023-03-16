package com.dimple.blog.front.service.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.dimple.blog.front.service.config.GithubTokenInfoConfig;
import com.dimple.blog.front.service.service.VisitorService;
import com.dimple.blog.front.service.service.bo.GithubVisitorInfoBO;
import com.dimple.common.core.utils.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * VisitorServiceImpl
 *
 * @author Dimple
 * @date 2023/3/14 13:38
 */
@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    GithubTokenInfoConfig githubTokenInfoConfig;

    @Override
    public GithubVisitorInfoBO getGithubVisitorInfo(String code) {
        GithubVisitorInfoBO githubVisitorInfoBO = new GithubVisitorInfoBO();
        String token = getAccessToken(code);
        if (StringUtils.isEmpty(token)) {
            return githubVisitorInfoBO;
        }
        JSONObject githubUserInfo = getGithubUserInfo(token);
        githubVisitorInfoBO.setLink(githubUserInfo.getString("html_url"));
        githubVisitorInfoBO.setEmail(githubUserInfo.getString("email"));
        githubVisitorInfoBO.setUsername(githubUserInfo.getString("name"));
        githubVisitorInfoBO.setLoginUsername(githubUserInfo.getString("login"));
        githubVisitorInfoBO.setAvatars(githubUserInfo.getString("avatar_url"));
        githubVisitorInfoBO.setId(githubUserInfo.getLong("id"));
        return githubVisitorInfoBO;
    }

    @SneakyThrows
    private JSONObject getGithubUserInfo(String accessToken) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .proxy(new  Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",7890)))
                .build();
        Request request = new Request.Builder()
                .url(githubTokenInfoConfig.getUserInfoUrl())
                .addHeader("Authorization", "token " + accessToken)
                .get()
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return JSONObject.parseObject(response.body().string());
    }

    @SneakyThrows
    private String getAccessToken(String code) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .proxy(new  Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",7890)))
                .build();
        FormBody formBody = new FormBody.Builder()
                .add("client_id", githubTokenInfoConfig.getClientId())
                .add("client_secret", githubTokenInfoConfig.getClientSecrets())
                .add("code", code)
                .build();
        Request request = new Request.Builder()
                .url(githubTokenInfoConfig.getAccessTokenUrl())
                .post(formBody)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        //access_token=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&scope=&token_type=bearer
        String responseStr = response.body().string();
        log.info("response is {} ",responseStr);
        return responseStr.substring(0, responseStr.indexOf("&scope")).replace("access_token=", "");
    }

}