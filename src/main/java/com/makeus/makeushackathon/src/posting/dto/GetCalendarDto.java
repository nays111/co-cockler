package com.makeus.makeushackathon.src.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetCalendarDto {
    private final int day;
    private final String postingEmoji;
}
