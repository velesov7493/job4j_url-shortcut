package ru.job4j.urlshortcut.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.job4j.urlshortcut.dto.ExceptionResponseDto;
import ru.job4j.urlshortcut.exceptions.JwtAuthenticationException;
import ru.job4j.urlshortcut.exceptions.ObjectNotFoundException;
import ru.job4j.urlshortcut.exceptions.OperationNotAcceptableException;

import java.util.Date;

@ControllerAdvice
public class DefaultAdvice {

    @ExceptionHandler(OperationNotAcceptableException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(
            OperationNotAcceptableException e
    ) {
        String message = "Операция не применима к таким исходным данным!";
        return
                new ResponseEntity<>(
                    new ExceptionResponseDto(message, new Date()),
                    HttpStatus.NOT_ACCEPTABLE
                );
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(JwtAuthenticationException e) {
        String msg = "Ошибка авторизации: неправильный логин и/или пароль!";
        ExceptionResponseDto resp = new ExceptionResponseDto(msg, new Date());
        return new ResponseEntity<>(resp, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleException(ObjectNotFoundException e) {
        String msg = "Объект не найден!";
        ExceptionResponseDto resp = new ExceptionResponseDto(msg, new Date());
        return new ResponseEntity<>(resp, HttpStatus.NOT_FOUND);
    }
}