package com.fourback.runus.global.error.exception;

import com.fourback.runus.global.error.errorCode.ResponseCode;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : NotFoundException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class NotFoundException extends CustomBaseException{
    public NotFoundException(ResponseCode responseCode){
        super(responseCode.getMessage(), responseCode);
    }
    public NotFoundException(){
        super(ResponseCode.NOT_FOUND);
    }
    public NotFoundException(String message){
        super(message, ResponseCode.NOT_FOUND);
    }
}
