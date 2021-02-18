package com.wang.book.Exception;

public class RepeatLendingException extends RuntimeException{

    //重复预约异常
        public RepeatLendingException(String message) {
            super(message);
        }

        public RepeatLendingException(String message, Throwable cause) {
            super(message, cause);
        }
}
