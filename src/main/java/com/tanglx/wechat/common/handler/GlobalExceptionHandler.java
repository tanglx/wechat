package com.tanglx.wechat.common.handler;


import com.tanglx.wechat.common.constant.BaseErrors;
import com.tanglx.wechat.common.exception.BaseException;
import com.tanglx.wechat.common.vo.base.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

/**
 * @Describe 全局异常处理
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public BaseResponse baseExceptionHandler(HttpServletResponse response, BaseException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        log.error(ex.getMessage(), ex);
        return new BaseResponse(BaseErrors.EX_OTHER_CODE.code, ex.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public BaseResponse bindExceptionHandler(HttpServletResponse response, BindException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String message = ex.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList())
                .toString();
        log.error(message, ex);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse constraintViolationExceptionHandler(HttpServletResponse response, ConstraintViolationException ex) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String message = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList())
                .toString();
        log.error(message, ex);
        return new BaseResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message);
    }
}
