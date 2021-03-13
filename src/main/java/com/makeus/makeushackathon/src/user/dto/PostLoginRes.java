package com.makeus.makeushackathon.src.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostLoginRes {
    private final Boolean isMember;
    private final String jwt;
}