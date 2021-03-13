package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.config.BaseResponseStatus;
import com.makeus.makeushackathon.src.tag.dto.GetRecommendationRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class TagController {

    @ResponseBody
    @GetMapping("/api/v1/postings-recommendation")
    @Operation(summary = "추천 게시물 조회 API")
    public BaseResponse<GetRecommendationRes> getRecommendationPosting() {
        try {
            GetRecommendationRes getRecommendationRes = null;
            return new BaseResponse<>(SUCCESS, getRecommendationRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
