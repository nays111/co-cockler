package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.config.BaseException;
import com.makeus.makeushackathon.src.tag.dto.GetRecommendationRes;
import com.makeus.makeushackathon.src.user.User;
import com.makeus.makeushackathon.src.user.UserService;
import com.makeus.makeushackathon.utils.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

import static com.makeus.makeushackathon.config.BaseResponseStatus.*;

@AllArgsConstructor
@Service
public class TagService {
    private final JwtService jwtService;
    private final UserService userService;
    private final TagRepository tagRepository;


    public GetRecommendationRes retrieveRecommendationPosting() throws BaseException {
        User user = userService.retrieveUserInfoByUserIdx(jwtService.getUserIdx());

        List<Tag> myTags = null;
        List<Tag> distinctMyTags = null;
        try {
            distinctMyTags = tagRepository.findDistinctByUserAndStatus(user, "ACTIVE");
            myTags = tagRepository.findAllByUserAndStatus(user, "ACTIVE");
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_POST_TAG);
        }

        List<Tag> existTag = tagRepository.findAllByStatus("ACTIVE");
        if(existTag == null || existTag.isEmpty()) {
            throw new BaseException(NOT_FOUND_TEG);
        }

        List<Tag> recommendationTagList = null;
        if (!(myTags == null || distinctMyTags == null || myTags.isEmpty() || distinctMyTags.isEmpty())) {
            String[] myTagName = new String[distinctMyTags.size()];
            int[] myCount = new int[distinctMyTags.size()];
            for (int i = 0; i < distinctMyTags.size(); i++) {
                for (Tag tag : distinctMyTags) {
                    myTagName[i] = tag.getTagName();
                }
            }
            for (Tag tag : myTags) {
                for (int i = 0; i < distinctMyTags.size(); i++) {
                    if (tag.getTagName().equals(myTagName[i])) {
                        myCount[i]++;
                    }
                }
            }
            int maxCont = myCount[0];
            int idx = 0;
            for (int i = 1; i < myCount.length; i++) {
                if (maxCont < myCount[i]) {
                    int temp = maxCont;
                    maxCont = myCount[i];
                    myCount[i] = maxCont;
                    idx = i;
                }
            }
            String targetTagName = myTagName[idx];
            try {
                recommendationTagList = tagRepository.findAllByUserNotAndStatusAndTagNameOrderByTagIdxDesc(user, "ACTIVE", targetTagName);
            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_POST_TAG);
            }
        } else {
            try {
                recommendationTagList = tagRepository.findAllByUserNotAndStatusOrderByTagIdxDesc(user, "ACTIVE");
            } catch (Exception exception) {
                throw new BaseException(FAILED_TO_POST_TAG);
            }
        }

        Random random = new Random();
        int randomIdx = random.nextInt(recommendationTagList.size());
        return new GetRecommendationRes(recommendationTagList.get(randomIdx).getPosting().getPostingIdx(),
                    "오늘의 추천 게시글입니다.\n이 게시물은 어때요?");
    }
}