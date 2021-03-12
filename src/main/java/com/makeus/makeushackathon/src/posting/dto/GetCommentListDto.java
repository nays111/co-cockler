package com.makeus.makeushackathon.src.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;

@Getter
@AllArgsConstructor
public class GetCommentListDto {
    private int userIdx;
    private String userNickname;
    private String commentCreatedDayBefore;
    private String commentDescription;
}
