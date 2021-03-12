package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.makeus.makeushackathon.config.BaseResponseStatus.FAILED_TO_GET_USER;
import static com.makeus.makeushackathon.config.BaseResponseStatus.NOT_FOUND_USER;
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
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
}
