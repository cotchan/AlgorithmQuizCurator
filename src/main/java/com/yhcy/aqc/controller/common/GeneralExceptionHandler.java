package com.yhcy.aqc.controller.common;

import com.yhcy.aqc.error.ServiceRuntimeException;
import org.hibernate.TypeMismatchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.yhcy.aqc.controller.common.ApiResult.ERROR;

/**
 * @ControllerAdvice 어노테이션을 사용하면 @RestController의 클래스에서 발생한 예외를 핸들링합니다.
 * API 서버에서 일관된 포맷의 에러 응답 메시지를 내려주기 위해 별도의 GeneralExceptionHandler를 사용
 */
@ControllerAdvice
public class GeneralExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 에러 코드에 맞게 Http Header를 내려주기 위해 ResponseEntity를 사용합니다.
     * HttpHeader + ApiResult
     */
    private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<>(ERROR(throwable, status), headers, status);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalStateException.class, IllegalArgumentException.class,
            TypeMismatchException.class, HttpMessageNotReadableException.class,
            MissingServletRequestParameterException.class, MultipartException.class,
    })
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.debug("Bad request exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 정의한 비즈니스 예외가 발생하는 경우 Exception Handling
     */
    @ExceptionHandler(ServiceRuntimeException.class)
    public ResponseEntity<?> handleServiceRuntimeException(ServiceRuntimeException e) {
//        if (e instanceof NotFoundException)
//            return newResponse(e, HttpStatus.NOT_FOUND);
//        if (e instanceof UnauthorizedException)
//            return newResponse(e, HttpStatus.UNAUTHORIZED);

        log.warn("Unexpected service exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 예측하지 못한 API Server Exception Handling
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("Unexpected exception occurred: {}", e.getMessage(), e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
