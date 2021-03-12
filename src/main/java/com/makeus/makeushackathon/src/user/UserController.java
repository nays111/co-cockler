package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.user.dto.PostUserNicknameReq;
import com.makeus.makeushackathon.src.user.dto.PostUserRes;
import com.makeus.makeushackathon.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@AllArgsConstructor
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;
    @ResponseBody
    @PostMapping("/api/v1/kakao-login")
    @Operation(summary = "카카오 로그인 API",description = "헤더에 KAKAO-ACCESS-TOKEN 을 입력해주세요" )
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
    @ResponseBody
    @PostMapping("/api/v1/users/nickname")
    @Operation(summary = "닉네임 작성(회원가입) API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<String> postNickname(@RequestBody PostUserNicknameReq postUserNicknameReq){
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        if(postUserNicknameReq.getNickname()==null || postUserNicknameReq.getNickname().length()==0){
            return new BaseResponse<>(EMPTY_USER_NICKNAME);
        }
        if(userService.isNicknameUsable(postUserNicknameReq.getNickname())==false){
            return new BaseResponse<>(DUPLICATED_NICKNAME);
        }
        try {
            userService.postUserNickname(userIdx,postUserNicknameReq);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}