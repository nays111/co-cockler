package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.user.dto.GetUserRes;
import com.makeus.makeushackathon.src.user.dto.PostLoginRes;
import com.makeus.makeushackathon.src.user.dto.PostUserReq;
import com.makeus.makeushackathon.src.user.dto.PostUserRes;
import com.makeus.makeushackathon.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/users/jwt")
    @Operation(summary = "JWT검증 API", description = "자동로그인")
    public BaseResponse<Void> jwt(@RequestHeader("X-ACCESS-TOKEN") String jwt) {
        try {
            Integer userIdx = jwtService.getUserIdx();
            if(!userService.isUserIdxUsable(userIdx)) {
                return new BaseResponse<>(NOT_FOUND_USER);
            }
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/api/v1/users/kakao-login")
    @Operation(summary = "카카오 로그인 API", description = "헤더에 KAKAO-ACCESS-TOKEN 을 입력해주세요.\n" +
            "isMember true 회원, isMember false 가입 필요" )
    public BaseResponse<PostLoginRes> postKakaoLogin(@RequestHeader("KAKAO-ACCESS-TOKEN") String accessToken) {
        try {
            PostLoginRes postLoginRes = userService.loginUser(accessToken);
            return new BaseResponse<>(SUCCESS, postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/api/v1/users")
    @Operation(summary = "닉네임 작성(회원가입) API", description = "헤더에 KAKAO-ACCESS-TOKEN 을 입력해주세요.")
    public BaseResponse<PostUserRes> postUser(@RequestHeader("KAKAO-ACCESS-TOKEN") String accessToken,
            @RequestBody PostUserReq postUserNicknameReq){
        if(postUserNicknameReq.getNickname()==null || postUserNicknameReq.getNickname().length()==0){
            return new BaseResponse<>(EMPTY_USER_NICKNAME);
        }
        try {
            PostUserRes postUserRes = userService.createUser(accessToken, postUserNicknameReq);
            return new BaseResponse<>(SUCCESS, postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/api/v1/users")
    @Operation(summary = "회원 정보 조회 API", description = "헤더에 ACCESS-TOKEN 을 입력해주세요.")
    public BaseResponse<GetUserRes> getUser(@RequestHeader("X-ACCESS-TOKEN") String accessToken) {
        try {
            GetUserRes getUserRes = userService.getUser();
            return new BaseResponse<>(SUCCESS, getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}