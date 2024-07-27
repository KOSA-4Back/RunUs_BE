package com.fourback.runus.global.error.exception;

import com.fourback.runus.global.error.errorCode.ResponseCode;

import lombok.Getter;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : CustomBaseException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 * 2024-06-26        김민지            CustomBaseException() 메서드 추가
 */
@Getter
public class CustomBaseException extends RuntimeException{
    private final ResponseCode responseCode;

    public CustomBaseException(String message, ResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public CustomBaseException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
    
    public CustomBaseException(String message) {
        super(message);
        this.responseCode = ResponseCode.INTERNAL_SERVER_ERROR;
    }
}