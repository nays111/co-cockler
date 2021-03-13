package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.user.dto.PostLoginRes;
import com.makeus.makeushackathon.src.user.dto.PostUserReq;
import com.makeus.makeushackathon.src.user.dto.PostUserRes;
import com.makeus.makeushackathon.utils.JwtService;
import com.makeus.makeushackathon.utils.SNSLogin;
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
    private final SNSLogin snsLogin;

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
    public Boolean isSocialIdxUsable(String socialId) {
        return !userRepository.existsBySocialIdAndStatus(socialId, "ACTIVE");
    }

    public PostUserRes createUser(String accessToken, PostUserReq parameters) throws BaseException{
        User newUser = new User(snsLogin.socialIdByKakao(accessToken), parameters.getNickname());

        try {
            newUser = userRepository.save(newUser);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        return new PostUserRes(newUser.getUserIdx(), jwtService.createJwt(newUser.getUserIdx()));
    }

    public PostLoginRes loginUser(String accessToken) throws BaseException {
        String socialId = snsLogin.socialIdByKakao(accessToken);

        User user = null;
        if(isSocialIdxUsable(socialId)) {
            try {
                user = userRepository.findBySocialId(socialId);
            } catch (Exception exception) {
                //
            }
            return new PostLoginRes(true, jwtService.createJwt(user.getUserIdx()));
        }

        return new PostLoginRes(false, null);
    }
}