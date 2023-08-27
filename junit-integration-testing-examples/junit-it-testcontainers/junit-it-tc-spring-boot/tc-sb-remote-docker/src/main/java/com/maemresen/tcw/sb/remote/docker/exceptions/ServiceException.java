package com.maemresen.tcw.sb.remote.docker.exceptions;

import com.maemresen.tcw.sb.remote.docker.utils.constants.ExceptionType;
import lombok.Getter;


@Getter
public class ServiceException extends RuntimeException {
    private final ExceptionType exceptionType;
    private final transient Object data;

    protected ServiceException(Throwable throwable, ExceptionType exceptionType) {
        super(throwable);
        this.exceptionType = exceptionType;
        this.data = null;
    }

    protected ServiceException(String message, Object data, ExceptionType exceptionType) {
        super(message);
        this.exceptionType = exceptionType;
        this.data = data;
    }

    protected ServiceException(String message, Throwable cause, ExceptionType exceptionType) {
        super(message, cause);
        this.exceptionType = exceptionType;
        this.data = null;
    }

    protected ServiceException(String message, Throwable cause, ExceptionType exceptionType, Object data) {
        super(message, cause);
        this.exceptionType = exceptionType;
        this.data = data;
    }


    protected ServiceException(Throwable cause, ExceptionType exceptionType, Object data) {
        super(cause);
        this.exceptionType = exceptionType;
        this.data = data;
    }

    @Override
    public String toString() {
        return String.format("%s:data=%s", exceptionType, data);
    }
}