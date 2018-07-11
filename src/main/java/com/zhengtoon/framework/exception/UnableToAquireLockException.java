package com.zhengtoon.framework.exception;

/**
 * @auther: qindaorong
 * @Date: 2018/7/10 15:41
 * @Description:
 */
public class UnableToAquireLockException extends RuntimeException{

    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
