package com.fourback.runus.global.error.exception;

import com.fourback.runus.global.error.errorCode.ResponseCode;


public class JwtValidationException extends CustomBaseException{

    public JwtValidationException(ResponseCode responseCode) {
        super(responseCode);
    }
}
