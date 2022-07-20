package com.tanglx.wechat.common.exception;


import com.tanglx.wechat.common.constant.BaseErrors;

/**
 * @Describe
 * @Author tanglingxiao
 * @Date 2022-05-25
 */
public class BaseException extends RuntimeException {
    private int status = 200;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public BaseException() {
    }

    public BaseException(String message, int status) {
        super(message);
        this.status = status;
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BaseException(BaseErrors baseErrors) {
        super(baseErrors.message);
        this.status = baseErrors.code;
    }
}
