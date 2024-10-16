package com.gambling.exceptions;

public class ScratchGameException extends RuntimeException {

    public ScratchGameException(String message) {
        super(message);
    }

    public ScratchGameException(Throwable cause) {
        super(cause);
    }
}
