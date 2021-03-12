package com.makeus.makeushackathon.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    // 1000 : 요청 성공
    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    // 2000 : Request 오류
    EMPTY_JWT(false, 2000, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2001, "유효하지 않은 JWT입니다."),











    // shine
    EMPTY_NICKNAME(),

    // 3000 : Response 오류
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    NOT_FOUND_USER(false, 3010, "존재하지 않는 회원입니다."),










    // shine
    KAKAO_CONNECTION(false, 3500, "카카오 연결에 실패하였습니다."),
    KAKAO_CONNECTION_1(false, 3501, "카카오 Response 에러 : 플랫폼 서비스의 일시적 내부 장애가 발생했습니다."),
    KAKAO_CONNECTION_2(false, 3502, "카카오 Response 에러 : 액세스 토큰 정보가 올바른 형식으로 요청했는지 확인해주세요."),
    KAKAO_CONNECTION_401(false, 3503, "카카오 Response 에러 : 유효하지 않은 앱키나 액세스 토큰으로 요청하였습니다."),
    KAKAO_CONNECTION_ETC(false, 3504, "카카오 Response 에러가 발생하였습니다."),

    // 4000 : Database 오류
    DATABASE_ERROR(false, 4001, "데이터베이스 오류가 발생하였습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}