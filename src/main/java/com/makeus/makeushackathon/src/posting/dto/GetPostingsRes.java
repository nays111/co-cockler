package com.makeus.makeushackathon.src.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPostingsRes {//홈 피드뷰 조회 DTO
    private final int postingIdx;
    private final String postingDescription;
    private final String postingThumbnailUrl;
    private final String postingPicture1Url;
    private final String getPostingPicture2Url;
    private final List<String> tagNameList;
    private final String createdDayBefore;
    private final int commentCount;
}
