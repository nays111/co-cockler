package com.makeus.makeushackathon.src.posting.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetCalendarRes {
    List<GetCalendarDto> getCalendarDtoList;
    List<GetMyPostingsRes> getMyPostingsResList;
}
