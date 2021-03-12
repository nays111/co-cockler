package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.user.dto.PostUserRes;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@AllArgsConstructor
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    @ResponseBody
    @PostMapping("/kakao-login")
    public BaseResponse<PostUserRes> postKakaoLogin()  {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("KAKAO-ACCESS-TOKEN");
        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_TOKEN);
        }
        try {
            PostUserRes postUserRes = userService.kakaoLogin(accessToken);
            return new BaseResponse<>(SUCCESS,postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}