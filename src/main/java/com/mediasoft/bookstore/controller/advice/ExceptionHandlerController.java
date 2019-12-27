package com.mediasoft.bookstore.controller.advice;

import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {
    //TODO все сообщения в формат JSON!
    @ResponseBody
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException enfe)
            throws EntityNotFoundException {
        return new ResponseEntity<>(enfe.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> validationException(MethodArgumentNotValidException manve)
            throws MethodArgumentNotValidException {
        return new ResponseEntity<>("Ошибка валидации.. " +
                manve.getBindingResult().getAllErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.toList()),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

/*    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> otherException(Exception e)
            throws Exception {
        return new ResponseEntity<>("Произошло что-то странное.. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
}