package com.wang.book.Exception;

public class NoNumberException extends RuntimeException{

    //库存不足异常
        public NoNumberException(String message) {
            super(message);
        }

        public NoNumberException(String message, Throwable cause) {
            super(message, cause);
        }

    }

