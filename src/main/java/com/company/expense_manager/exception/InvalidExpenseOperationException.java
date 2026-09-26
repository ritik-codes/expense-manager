package com.company.expense_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidExpenseOperationException extends RuntimeException {
    public InvalidExpenseOperationException(String message) {
        super(message);
    }
}
