package com.makeus.makeushackathon.src.user;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.user.dto.PostLoginRes;
import com.makeus.makeushackathon.src.user.dto.PostSignUpReq;
import com.makeus.makeushackathon.src.user.dto.PostSignUpRes;
import com.makeus.makeushackathon.utils.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@AllArgsConstructor
@RestController
@CrossOrigin
public class UserController {
    private final JwtService jwtService;
    private final UserService userService;
    /**
     * JWT 검증 API
     * [GET] /users/jwt
     * @RequestHeader X-ACCESS-TOKEN
     * @return BaseResponse<Void>
     * @Auther shine
     */
    @GetMapping("/users/jwt")
    public BaseResponse<Void> jwt(@RequestHeader("X-ACCESS-TOKEN") String jwt) {
        try {
            Integer userId = jwtService.getUserIdx();
            // TODO 회원조회
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 카카오 로그인
     * [POST] /users/login-kakao
     * @RequestHeader X-ACCESS-TOKEN
     * @return BaseResponse<PostLoginRes>
     * @Auther shine
     */
    @ResponseBody
    @PostMapping("/users/login-kakao")
    public BaseResponse<PostLoginRes> postLoginKakao(@RequestHeader("X-ACCESS-TOKEN") String token) {
        try {
            PostLoginRes postLoginRes = userService.login(SNSLogin.userIdFromKakao(token));
            return new BaseResponse<>(SUCCESS, postLoginRes);
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 카카오 회원가입
     * [POST] /app/users/kakao
     * @RequestHeader X-ACCESS-TOKEN
     * @return BaseResponse<Void>
     * @Auther shine
     */
    @ResponseBody
    @PostMapping("/users/kakao")
    public BaseResponse<PostSignUpRes> postSignUpKakao(@RequestHeader("X-ACCESS-TOKEN") String token,
                                                       @RequestBody(required = false) PostSignUpReq parameters) {
        if(parameters.getNickname() == null || parameters.getNickname().length() == 0) {
            // TODO
        }

        try {
            PostSignUpRes postSignUpRes = userService.createUser(parameters, SNSLogin.userIdFromKakao(token), "K", token);
            return new BaseResponse<>(SUCCESS, postSignUpRes);
        } catch(BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}