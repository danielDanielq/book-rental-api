package com.bookrental.app.exception;

public class NoAddressInRequestException extends RuntimeException {
    public NoAddressInRequestException(String message) {
        super(message);
    }
}
