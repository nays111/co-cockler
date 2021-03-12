package com.makeus.makeushackathon.src.posting;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.posting.dto.PostPostingReq;
import com.makeus.makeushackathon.src.tag.Tag;
import com.makeus.makeushackathon.src.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final TagRepository tagRepository;
    @Transactional
    public void postPosting(int userIdx, PostPostingReq postPostingReq) throws BaseException {
        //todo user 찾는 과정 필요
        //Posting posting = new Posting();
        List<String> tagList = postPostingReq.getTagNameList();
        for(int i=0;i<tagList.size();i++){
            String tagName = tagList.get(i);
            Tag tag  = new Tag();//todo user+posting+tagName;
            tagRepository.save(tag);
        }


    }
}
