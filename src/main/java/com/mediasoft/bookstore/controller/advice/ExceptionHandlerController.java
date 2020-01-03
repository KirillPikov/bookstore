package com.mediasoft.bookstore.controller.advice;

import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.exception.ShoppingBasketReleaseException;
import com.mediasoft.bookstore.exception.ShoppingBasketUpdateException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

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

    @ResponseBody
    @ExceptionHandler(ShoppingBasketReleaseException.class)
    public ResponseEntity<String> shoppingBasketReleaseException(ShoppingBasketReleaseException sbre)
            throws ShoppingBasketReleaseException {
        return new ResponseEntity<>("Ошибка назначения корзине статуса ORDERED.. " + sbre.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ExceptionHandler(ShoppingBasketUpdateException.class)
    public ResponseEntity<String> shoppingBasketUpdateException(ShoppingBasketUpdateException sbue)
            throws ShoppingBasketUpdateException {
        return new ResponseEntity<>("Ошибка обновления состояния корзины.. " + sbue.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> httpMessageNotReadableException(HttpMessageNotReadableException hmnre)
            throws HttpMessageNotReadableException {
        return new ResponseEntity<>("Ошибка разбора запроса.. " + hmnre.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> otherException(Exception e)
            throws Exception {
        return new ResponseEntity<>("Произошло что-то странное.. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}