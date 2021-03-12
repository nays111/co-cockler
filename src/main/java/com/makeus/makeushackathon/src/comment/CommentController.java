package com.makeus.makeushackathon.src.comment;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.comment.dto.PostCommentReq;
import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import com.makeus.makeushackathon.utils.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CommentController {
    private final JwtService jwtService;
    private final CommentService commentService;
    @ResponseBody
    @PostMapping("/api/v1/postings/{postingIdx}/comments")
    @Operation(summary = "댓글 작성 API",description = "JWT 토큰이 필요합니다.")
    public BaseResponse<String> postPosting(@RequestBody PostCommentReq postCommentReq,
                                            @PathVariable(required = true) int postingIdx){
        int userIdx;
        try{
            userIdx = jwtService.getUserIdx();
        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        if(postCommentReq.getCommentDescription()==null || postCommentReq.getCommentDescription().length()==0){
            return new BaseResponse<>(EMPTY_COMMENT_DESCRIPTION);
        }
        try{
            commentService.postPosting(userIdx,postingIdx,postCommentReq);
            return new BaseResponse<>(SUCCESS);
        }catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
