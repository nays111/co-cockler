package com.makeus.makeushackathon.src.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetPostingRes {
    private final int postingIdx;
    private final String postingDescription;
    private List<String> tagNameList;
    private final String createdDayBefore;
    private final int commentCount;
    List<GetCommentListDto> getCommentList;
}
