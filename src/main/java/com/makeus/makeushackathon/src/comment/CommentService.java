package com.makeus.makeushackathon.src.comment;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.comment.dto.PostCommentReq;
import com.makeus.makeushackathon.src.posting.Posting;
import com.makeus.makeushackathon.src.posting.PostingRepository;
import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import com.makeus.makeushackathon.src.tag.Tag;
import com.makeus.makeushackathon.src.user.User;
import com.makeus.makeushackathon.src.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserService userService;
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    @Transactional
    public void postPosting(int userIdx, int postingIdx, PostCommentReq postCommentReq) throws BaseException {
        User user = userService.retrieveUserInfoByUserIdx(userIdx);
        Posting posting = postingRepository.findAllByPostingIdxAndStatus(postingIdx,"ACTIVE");
        if(posting==null){
            throw new BaseException(FAILED_TO_GET_POSTING);
        }
        String commentDescription = postCommentReq.getCommentDescription();
        Comment comment = new Comment(user,posting,commentDescription);
        try{
            commentRepository.save(comment);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_COMMENT);
        }

    }
}
