package com.wang.book.Exception;

public class LendingException extends RuntimeException{
    public LendingException(String message) {
        super(message);
    }

    public LendingException(String message, Throwable cause) {
        super(message, cause);
    }
}
