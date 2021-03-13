package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.user.dto.GetUserRes;
import com.makeus.makeushackathon.src.user.dto.PostLoginRes;
import com.makeus.makeushackathon.src.user.dto.PostUserReq;
import com.makeus.makeushackathon.src.user.dto.PostUserRes;
import com.makeus.makeushackathon.utils.JwtService;
import com.makeus.makeushackathon.utils.SNSLogin;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SNSLogin snsLogin;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 가입");

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

    public boolean isUserIdxUsable(Integer userIdx) {
        return userRepository.existsByUserIdxAndStatus(userIdx, "ACTIVE");
    }

    public Boolean isSocialIdxUsable(String socialId) {
        return userRepository.existsBySocialIdAndStatus(socialId, "ACTIVE");
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
                throw new BaseException(FAILED_TO_GET_USER);
            }
            return new PostLoginRes(true, jwtService.createJwt(user.getUserIdx()));
        }
        return new PostLoginRes(false, null);
    }

    public GetUserRes getUser() throws BaseException {
        int userIdx = jwtService.getUserIdx();
        User user = null;
        try {
            user = userRepository.findByUserIdxAndStatus(userIdx, "ACTIVE");
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_GET_USER);
        }
        if (user == null) {
            throw new BaseException(NOT_FOUND_USER);
        }
        return new GetUserRes(user.getUserIdx(), user.getNickname(),
                "카카오", dateFormat.format(user.getCreatedAt()));
    }
}