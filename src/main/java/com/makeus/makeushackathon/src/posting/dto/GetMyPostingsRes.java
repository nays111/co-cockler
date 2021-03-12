package com.makeus.makeushackathon.src.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetMyPostingsRes {
    private final int postingIdx;
    private final String postingDate;
    private final String postingThumbnailUrl;
    private final String postingDescription;

}
