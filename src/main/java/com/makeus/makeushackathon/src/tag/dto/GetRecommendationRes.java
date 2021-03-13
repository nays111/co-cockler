package com.makeus.makeushackathon.src.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetRecommendationRes {
    private final Integer postingIdx;
    private final String title;
}