package ru.jdrims.moneytransferservice.exception;

public class ErrorConfirmException extends RuntimeException {
    public ErrorConfirmException(String message) {
        super(message);
    }
}
