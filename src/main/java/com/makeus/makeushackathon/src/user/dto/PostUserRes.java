package com.makeus.makeushackathon.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostUserRes {
    private final Integer userIdx;
    private final String jwt;
}