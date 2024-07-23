package com.fourback.runus.global.error.exception;

import com.fourback.runus.global.error.errorCode.ResponseCode;

/**
 * packageName    : org.omsf.error.Exception
 * fileName       : ResourceNotFoundException
 * author         : Yeong-Huns
 * date           : 2024-06-18
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-06-18        Yeong-Huns       최초 생성
 */
public class ResourceNotFoundException extends NotFoundException {
    public ResourceNotFoundException() {
        super(ResponseCode.NOT_FOUND);
    }
    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(ResponseCode responseCode) {
        super(responseCode);
    }
}
