package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseResponse;
import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class PostingController {
    @ResponseBody
    @PostMapping("/api/v1/posting")
    public BaseResponse<String> postPosting(@RequestBody PostPostingReq postPostingReq){
        //todo jwt 검증 필요
        if(postPostingReq.getPostingDescription()==null || postPostingReq.getPostingDescription().length()==0){
            return new BaseResponse<>(EMPTY_POSTING_DESCRIPTION);
        }


    }
}
