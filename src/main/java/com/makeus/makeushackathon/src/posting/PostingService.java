package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostingService {
    @Transactional
    public void postPosting(int userIdx, PostPostingReq postPostingReq){
        //todo user 찾는 과정 필요
        //Posting posting = new Posting();


    }
}
