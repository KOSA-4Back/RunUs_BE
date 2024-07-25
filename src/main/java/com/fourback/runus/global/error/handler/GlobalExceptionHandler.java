package com.fourback.runus.global.error.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fourback.runus.global.error.errorCode.ResponseCode;
import com.fourback.runus.global.error.exception.CustomBaseException;
import com.fourback.runus.global.error.exception.JwtValidationException;
import com.fourback.runus.global.error.exception.ResourceNotFoundException;
import com.fourback.runus.global.error.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;

/**
 * packageName    : com.fourback.runus.global.error.handler
 * fileName       : GlobalExceptionHandler
 * author         : Yeong-Huns
 * date           : 2024-07-23
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-07-23        Yeong-Huns       최초 생성
 */
@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class  GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e){
        log.error("Response: {}", ErrorResponse.of(ResponseCode.METHOD_NOT_ALLOWED , " [Detail log] : "+e.getMessage()));
        return createErrorResponse(ResponseCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(CustomBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(CustomBaseException e){
        log.error("Response: {}", ErrorResponse.of(e.getResponseCode(),  " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(e.getResponseCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e){
        log.error("MethodArgumentNotValidException", e);
        return createErrorResponse(ResponseCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handle(HttpMessageNotReadableException e){
        log.error("HttpMessageNotReadableException", e);
        return createErrorResponse(ResponseCode.NOT_VALID_JSON);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e){
        log.error("Response: {}", ErrorResponse.of(ResponseCode.NOT_FOUND , " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ResponseCode.NOT_FOUND);
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ErrorResponse> handle(JsonProcessingException e){
        log.error("Response: {}", ErrorResponse.of(ResponseCode.NOT_VALID_JSON, " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ResponseCode.NOT_VALID_JSON);
    }


    //최종 에러처리
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e){
        log.error("Response: {}", ErrorResponse.of(ResponseCode.INTERNAL_SERVER_ERROR , " [Detail Message] : "+e.getMessage()));
        return createErrorResponse(ResponseCode.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handle(AccessDeniedException e) {
        log.error("Response: {}", ErrorResponse.of(ResponseCode.METHOD_NOT_ALLOWED, " 🥲[상세 메세지] : " + e.getMessage()));
        return createErrorResponse(ResponseCode.METHOD_NOT_ALLOWED);
    }


    @ExceptionHandler(JwtValidationException.class)
    protected ResponseEntity<ErrorResponse> handleJwtValidationException(JwtValidationException e) {
        log.error("Response: {}", ErrorResponse.of(e.getResponseCode(), " 🥲[상세 메세지] : " + e.getMessage()));
        return createErrorResponse(e.getResponseCode());
    }


    private ResponseEntity<ErrorResponse> createErrorResponse(ResponseCode responseCode){
        return new ResponseEntity<>(
                ErrorResponse.of(responseCode),
                responseCode.getStatus());
    }
}