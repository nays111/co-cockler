package com.makeus.makeushackathon.utils;

import com.makeus.makeushackathon.config.BaseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@Service
public class SNSLogin {
    /**
     * 카카오 토큰을 이용한 회원아이디 추출
     *
     * @param String token
     * @return String
     * @throws BaseException
     * @Auther shine
     */
    public String userIdFromKakao(String token) throws BaseException {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObj = null;
        String userId = "";

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://kapi.kakao.com/v1/user/access_token_info")
                    .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
                    .build();
            Response response = client.newCall(request).execute();
            jsonObj = (JsonObject) jsonParser.parse(response.body().string());
        } catch (Exception exception) {
            throw new BaseException(KAKAO_CONNECTION);
        }

        try {
            userId = jsonObj.get("id").toString();
        } catch (Exception exception1) {
            try {
                if (jsonObj.get("code").toString().equals("-1")) {
                    throw new BaseException(KAKAO_CONNECTION_1);
                } else if (jsonObj.get("code").toString().equals("-2")) {
                    throw new BaseException(KAKAO_CONNECTION_2);
                } else if (jsonObj.get("code").toString().equals("-401")) {
                    throw new BaseException(KAKAO_CONNECTION_401);
                } else {
                    throw new BaseException(KAKAO_CONNECTION_ETC);
                }
            } catch (Exception exception2) {
                throw new BaseException(KAKAO_CONNECTION_ETC);
            }
        }
        return userId;
    }
}