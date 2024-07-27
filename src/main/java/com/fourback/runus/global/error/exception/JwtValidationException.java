package com.fourback.runus.global.error.exception;

import com.fourback.runus.global.error.errorCode.ResponseCode;


/**
 * packageName    : com.fourback.runus.global.error.exception
 * fileName       : JwtValidationException
 * author         : 김은정
 * date           : 2024-07-24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-24        김은정            최초 생성
 */
public class JwtValidationException extends CustomBaseException{

    public JwtValidationException(ResponseCode responseCode) {
        super(responseCode);
    }
}
