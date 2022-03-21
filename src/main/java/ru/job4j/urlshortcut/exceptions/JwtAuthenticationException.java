package ru.job4j.urlshortcut.exceptions;

public class JwtAuthenticationException extends Exception {

    public JwtAuthenticationException() { }

    public JwtAuthenticationException(String message) {
        super(message);
    }

    public JwtAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtAuthenticationException(Throwable cause) {
        super(cause);
    }
}
