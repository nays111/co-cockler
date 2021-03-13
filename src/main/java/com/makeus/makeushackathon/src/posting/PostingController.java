package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.posting.dto.*;
import com.makeus.makeushackathon.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostingController {
    private final PostingService postingService;
    private final JwtService jwtService;


    @ResponseBody
    @PostMapping("/api/v1/postings")
    @Operation(summary = "게시물 작성 API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<String> postPosting(@RequestBody PostPostingReq postPostingReq){
        //jwt 검증
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
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
        //jwt 검증
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        try{
            List<GetPostingsRes> getPostingResList = postingService.getPostings();
            return new BaseResponse<>(SUCCESS,getPostingResList);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/api/v1/postings/{postingIdx}")
    @Operation(summary="게시물 상세 조회 API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<GetPostingRes> getPosting(@PathVariable(required = true)int postingIdx){
        //jwt 검증
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        try{
            GetPostingRes getPostingRes= postingService.getPosting(postingIdx);
            return new BaseResponse<>(SUCCESS,getPostingRes);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("/api/v1/calendars")
    @Operation(summary = "달력 조회 API", description = "JWT 토큰이 필요합니다.")
    public BaseResponse<GetCalendarRes> getCalendar(@RequestParam(required = true)String year,
                                                          @RequestParam(required = true)String month){
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        try{
            GetCalendarRes getCalendarRes = postingService.getCalendar(userIdx,year,month);
            return new BaseResponse<>(SUCCESS, getCalendarRes);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @ResponseBody
    @GetMapping("api/v1/my-postings")
    @Operation(summary = "달력 상세 조회 API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<List<GetMyPostingsRes>> getMyPosting(@RequestParam(required = true) String year,
                                                             @RequestParam(required = true) String month,
                                                             @RequestParam(required = true) String day){
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        try{
            List<GetMyPostingsRes> getMyPostingsResList = postingService.getMyPosting(userIdx,year,month,day);
            return new BaseResponse<>(SUCCESS,getMyPostingsResList);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }



}
