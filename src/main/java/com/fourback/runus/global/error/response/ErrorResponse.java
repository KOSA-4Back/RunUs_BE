package com.fourback.runus.global.error.response;

import com.fourback.runus.global.error.errorCode.ResponseCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * packageName    : org.omsf.error.response
 * fileName       : ErrorResponse
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String Message;
    private String code;

    public ErrorResponse(final ResponseCode code) {
        this.Message = code.getMessage();
        this.code = code.getCode();
    }

    public ErrorResponse(final ResponseCode code, final String message) {
        this.Message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ResponseCode code) {
        return new ErrorResponse(code);
    }

    public static ErrorResponse of(final ResponseCode code, final String message) {
        return new ErrorResponse(code, message);
    }
}
