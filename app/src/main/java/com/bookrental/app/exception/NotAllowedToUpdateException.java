package com.bookrental.app.exception;

public class NotAllowedToUpdateException extends RuntimeException {
    public NotAllowedToUpdateException(String message) {
        super(message);
    }
}
