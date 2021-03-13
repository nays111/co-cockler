package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.tag.dto.GetRecommendationRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class TagController {
    private final TagService tagService;

    @ResponseBody
    @GetMapping("/api/v1/postings-recommendation")
    @Operation(summary = "추천 게시물 조회 API", description = "헤더에 X-ACCESS-TOKEN 을 입력해주세요.")
    public BaseResponse<GetRecommendationRes> getRecommendationPosting(@RequestHeader("X-ACCESS-TOKEN") String accessTokeng) {
        try {
            GetRecommendationRes getRecommendationRes = tagService.retrieveRecommendationPosting();
            return new BaseResponse<>(SUCCESS, getRecommendationRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
