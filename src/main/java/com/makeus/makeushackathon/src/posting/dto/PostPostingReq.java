package com.makeus.makeushackathon.src.posting.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class PostPostingReq {
    private String postingDescription;
    private String postingThumbnailUrl;
    private String postingPicture1Url;
    private String getPostingPicture2Url;
    private List<String> tagNameList;
}
