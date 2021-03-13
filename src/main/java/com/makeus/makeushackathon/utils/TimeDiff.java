package com.makeus.makeushackathon.utils;

import org.springframework.stereotype.Service;


class TIME_MAXIMUM {

    public static final int SEC = 60;
    public static final int MIN = 60;
    public static final int HOUR = 24;
    public static final int DAY = 30;
    public static final int MONTH = 12;
}
@Service
public class TimeDiff {
    public String timeDiff(long regTime,long curTime){
        String createdDayBefore="";
        long diffTime = (curTime-regTime)/1000;
        if(diffTime < TIME_MAXIMUM.SEC) {
            createdDayBefore = diffTime + "초전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            createdDayBefore = diffTime + "분전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            createdDayBefore = (diffTime ) + "시간전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            createdDayBefore = (diffTime ) + "일전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            createdDayBefore = (diffTime ) + "달전";
        } else {
            createdDayBefore = (diffTime) + "년전";
        }
        return createdDayBefore;
    }
}
