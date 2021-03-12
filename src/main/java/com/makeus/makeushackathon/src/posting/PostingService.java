package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import com.makeus.makeushackathon.src.tag.Tag;
import com.makeus.makeushackathon.src.tag.TagRepository;
import com.makeus.makeushackathon.src.user.User;
import com.makeus.makeushackathon.src.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.makeus.makeushackathon.config.BaseResponseStatus.FAILED_TO_POST_POSTING;
import static com.makeus.makeushackathon.config.BaseResponseStatus.FAILED_TO_POST_TAG;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final TagRepository tagRepository;
    private final UserService userService;

    @Transactional
    public void postPosting(int userIdx, PostPostingReq postPostingReq) throws BaseException {
        User user = userService.retrieveUserInfoByUserIdx(userIdx);
        Posting posting = new Posting(postPostingReq.getPostingDescription(),postPostingReq.getPostingEmoji(),postPostingReq.getPostingThumbnailUrl(),
                postPostingReq.getPostingPicture1Url(),postPostingReq.getGetPostingPicture2Url());
        try{
            postingRepository.save(posting);
        }catch (Exception exception){
            throw new BaseException(FAILED_TO_POST_POSTING);
        }
        List<String> tagList = postPostingReq.getTagNameList();
        for(int i=0;i<tagList.size();i++){
            String tagName = tagList.get(i);
            Tag tag  = new Tag(user,posting,tagName);
            try{
                tagRepository.save(tag);
            }catch (Exception exception){
                throw new BaseException(FAILED_TO_POST_TAG);
            }
        }

    }
}
