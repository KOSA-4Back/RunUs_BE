package com.fourback.runus.global.error.exception;

import com.fourback.runus.global.error.errorCode.ResponseCode;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : BadRequestException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class InternalServerException extends CustomBaseException {
    public InternalServerException(ResponseCode responseCode){
        super(responseCode.getMessage(), responseCode);
    }
    public InternalServerException(){
        super(ResponseCode.INVALID_INPUT_VALUE);
    }
    public InternalServerException(String message){
        super(message, ResponseCode.INTERNAL_SERVER_ERROR);
    }
}