package ru.jdrims.moneytransferservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.jdrims.moneytransferservice.exception.ErrorConfirmException;
import ru.jdrims.moneytransferservice.exception.InvalidDataException;
import ru.jdrims.moneytransferservice.logger.Logger;
import ru.jdrims.moneytransferservice.model.ErrorResponse;

import java.util.concurrent.atomic.AtomicInteger;

@ControllerAdvice
public class MoneyTransferAdviceController {
    private final AtomicInteger id = new AtomicInteger(0);
    private final Logger logger = new Logger();

    @ExceptionHandler(ErrorConfirmException.class)
    public ResponseEntity<ErrorResponse> handleErrorResponse(ErrorConfirmException e) {
        logger.log(e.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage(), id.incrementAndGet()));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(InvalidDataException e) {
        logger.log(e.getMessage());
        return ResponseEntity.internalServerError().body(new ErrorResponse(e.getMessage(), id.incrementAndGet()));
    }
}
