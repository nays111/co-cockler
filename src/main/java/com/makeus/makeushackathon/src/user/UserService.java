package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.user.dto.PostUserRes;
import com.makeus.makeushackathon.utils.JwtService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public User retrieveUserInfoByUserIdx(int userIdx) throws BaseException {
        User user;
        try {
            user = userRepository.findById(userIdx).orElse(null);
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_GET_USER);
        }
        if (user == null || !user.getStatus().equals("ACTIVE")) {
            throw new BaseException(NOT_FOUND_USER);
        }
        return user;
    }


    public PostUserRes kakaoLogin(String accessToken) throws BaseException {
        JSONObject jsonObject;

        String header = "Bearer " + accessToken; // Bearer 다음에 공백 추가
        String apiURL = "https://kapi.kakao.com/v2/user/me";

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("Authorization", header);

        HttpURLConnection con;
        try {
            URL url = new URL(apiURL);
            con = (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new BaseException(WRONG_URL);
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_CONNECT);
        }

        String body;
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> rqheader : requestHeaders.entrySet()) {
                con.setRequestProperty(rqheader.getKey(), rqheader.getValue());
            }

            int responseCode = con.getResponseCode();
            InputStreamReader streamReader;
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                streamReader = new InputStreamReader(con.getInputStream());
            } else { // 에러 발생
                streamReader = new InputStreamReader(con.getErrorStream());
            }

            BufferedReader lineReader = new BufferedReader(streamReader);
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            body = responseBody.toString();
        } catch (IOException e) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        } finally {
            con.disconnect();
        }

        if (body.length() == 0) {
            throw new BaseException(FAILED_TO_READ_RESPONSE);
        }
        System.out.println(body);

        String socialId;
        String response;
        try{
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject) jsonParser.parse(body);
            socialId = "kakao_"+jsonObject.get("id").toString();
            response = jsonObject.get("kakao_account").toString();
        }
        catch (Exception e){
            throw new BaseException(FAILED_TO_PARSE);
        }

        String profilePhoto=null;
        String userName=null;
        String email=null;
        String phoneNumber=null;
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject responObj = (JSONObject) jsonParser.parse(response);
            if(responObj.get("email")!=null) {
                email = responObj.get("email").toString();
            }
            String profile = responObj.get("profile").toString();
            JSONObject profileObj = (JSONObject) jsonParser.parse(profile);
            userName = profileObj.get("nickname").toString();

            if(profileObj.get("profile_image")!=null) {
                profilePhoto = profileObj.get("profile_image").toString();
            }
        } catch (Exception e){
            throw new BaseException(FAILED_TO_PARSE);
        }

        User user = null;
        try {
            user = userRepository.findBySocialId(socialId);
        } catch (Exception ignored) {
            throw new BaseException(DATABASE_ERROR);
        }
        if (user == null) {
            user = new User(socialId,userName);
            try {
                user = userRepository.save(user);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        }
        else{
            if(!user.getStatus().equals("ACTIVE")) {
                user.setStatus("ACTIVE");
                try {
                    user = userRepository.save(user);
                } catch (Exception exception) {
                    throw new BaseException(DATABASE_ERROR);
                }
            }
        }
        try {
            Integer userIdx = user.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostUserRes(userIdx, jwt);
        }catch(Exception e){
            throw new BaseException(FAILED_TO_KAKAO_LOGIN);
        }
    }
}
