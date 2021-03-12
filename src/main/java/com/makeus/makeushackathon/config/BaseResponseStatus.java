package com.makeus.makeushackathon.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    SUCCESS_READ_INQUIRIES(true,1509,"1:1문의 조회에 성공하였습니다."),
    // 2000 : Request 오류
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_USERID(false, 2001, "유저 아이디 값을 확인해주세요."),
    EMPTY_JWT(false, 2010, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2011, "유효하지 않은 JWT입니다."),
    EMPTY_POSTING_DESCRIPTION(false,2012,"게시글 내용을 입력해주세요."),
    EMPTY_POSTING_THUMBNAIL_URL(false,2013,"최소 사진한장은 등록해주세요."),
    EMPTY_POSTING_EMOJI(false,2014,"이모지를 입력해주세요."),
    MAX_TAG_SIZE(false,2015,"태그는 최대 다섯개까지 입력가능합니다."),



    // 3000 : Response 오류
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    NOT_FOUND_USER(false, 3010, "존재하지 않는 회원입니다."),
    FAILED_TO_GET_USER(false,3011,"회원 조회에 실패하였습니다."),
    FAILED_TO_POST_POSTING(false,3012,"게시물 저장에 실패하였습니다."),
    FAILED_TO_POST_TAG(false,3013,"태크 저장에 실패하였습니다."),

    // 4000 : Database 오류
    SERVER_ERROR(false, 4000, "서버와의 통신에 실패하였습니다."),
    DATABASE_ERROR(false, 4001, "데이터베이스 연결에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
