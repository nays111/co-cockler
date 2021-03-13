package com.makeus.makeushackathon.src.tag;

import com.makeus.makeushackathon.src.tag.dto.GetRecommendationRes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TagService {

    public GetRecommendationRes retrieveRecommendationPosting() {



        return new GetRecommendationRes(null, "오늘의 추천 게시글입니다.\n" +
                "이 게시물은 어때요?");
    }

}
