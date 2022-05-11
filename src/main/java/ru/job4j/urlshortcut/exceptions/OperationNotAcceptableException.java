package ru.job4j.urlshortcut.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class OperationNotAcceptableException extends Exception {

    public OperationNotAcceptableException() { }

    public OperationNotAcceptableException(String message) {
        super(message);
    }

    public OperationNotAcceptableException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationNotAcceptableException(Throwable cause) {
        super(cause);
    }
}
