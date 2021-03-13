package com.makeus.makeushackathon.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetUserRes {
    private final Integer userIdx;
    private final String nickname;
    private final String socialType;
    private final String createDate;
}