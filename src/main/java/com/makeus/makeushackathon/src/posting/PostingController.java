package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.posting.dto.GetPostingsRes;
import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.makeus.makeushackathon.config.BaseResponseStatus.SUCCESS;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostingController {
    private final PostingService postingService;


    @ResponseBody
    @PostMapping("/api/v1/postings")
    @Operation(summary = "게시물 작성 API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<String> postPosting(@RequestBody PostPostingReq postPostingReq){
        //todo jwt 검증 필요
        if(postPostingReq.getPostingDescription()==null || postPostingReq.getPostingDescription().length()==0){
            return new BaseResponse<>(EMPTY_POSTING_DESCRIPTION);
        }
        //tag 필수아님, image 썸네일은 필수,
        if(postPostingReq.getPostingThumbnailUrl()==null || postPostingReq.getPostingThumbnailUrl().length()==0){
            return new BaseResponse<>(EMPTY_POSTING_THUMBNAIL_URL);
        }
        if(postPostingReq.getPostingEmoji()==null || postPostingReq.getPostingEmoji().length()==0){
            return new BaseResponse<>(EMPTY_POSTING_EMOJI);
        }
        if(postPostingReq.getTagNameList().size()>5){
            return new BaseResponse<>(MAX_TAG_SIZE);
        }
        try{
            postingService.postPosting(userIdx,postPostingReq);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/api/v1/postings")
    @Operation(summary = "피드 게시물 리스트 조회 API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<List<GetPostingsRes>> getPostings(){

    }

    @ResponseBody

}
