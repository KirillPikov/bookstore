package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.ShoppingBasketBookDto;
import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.ShoppingBasketBookMapper;
import com.mediasoft.bookstore.service.ShoppingBasketBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping(PathSettings.SHOPPING_BASKET_BOOK_CONTROLLER_PATH)
public class ShoppingBasketBookController {

    private final ShoppingBasketBookService shoppingBasketBookService;

    private final ShoppingBasketBookMapper shoppingBasketBookMapper;

    @GetMapping
    public ResponseEntity<List<ShoppingBasketBookDto>> getShoppingBasketBookPage(
            @PathVariable(
                    name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME
            ) Long customerId,
            @PathVariable(
                    name = PathSettings.SHOPPING_BASKET_NUM_PATH_VAR_NAME
            ) Integer shoppingBasketNum
    ) throws EntityNotFoundException {
        /* Получаем из сервиса все позиции находящиеся в данной корзине */
        List<ShoppingBasketBookDto> shoppingBasketBookDtos = shoppingBasketBookService.getShoppingBasketBookPage(customerId, shoppingBasketNum)
                .stream()
                /* Конвертирование каждой сущности в Dto */
                .map(shoppingBasketBookMapper::toDto)
                /* Сбор всех Dto в список */
                .collect(Collectors.toList());
        return new ResponseEntity<>(shoppingBasketBookDtos, HttpStatus.OK);
    }

    @GetMapping("/{" + PathSettings.SHOPPING_BASKET_BOOK_NUM_PATH_VAR_NAME + "}")
    public ResponseEntity<ShoppingBasketBookDto> getShoppingBasketListByCustomerId(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_NUM_PATH_VAR_NAME) Integer shoppingBasketNum,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_BOOK_NUM_PATH_VAR_NAME) Integer shoppingBasketBookNum) {

        /* Конвертирование в Dto */
        ShoppingBasketBookDto shoppingBasketBookDto = shoppingBasketBookMapper.toDto(
                /* Получаем из сервиса данную позицию в данной корзине */
                shoppingBasketBookService.getShoppingBasketBook(customerId, shoppingBasketNum, shoppingBasketBookNum)
        );
        return new ResponseEntity<>(shoppingBasketBookDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addShoppingBasket(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_NUM_PATH_VAR_NAME) Integer shoppingBasketNum,
            @RequestBody @Valid ShoppingBasketBookDto shoppingBasketBookDto) {

        /* Получение данной корзины с помощью сервиса */
        ShoppingBasketBook shoppingBasketBook = shoppingBasketBookMapper.toEntity(shoppingBasketBookDto);
        /* Добавление новой позиции в данную корзину */
        shoppingBasketBookService.addShoppingBasketBook(customerId, shoppingBasketNum, shoppingBasketBook);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/{" + PathSettings.SHOPPING_BASKET_BOOK_NUM_PATH_VAR_NAME + "}")
    public ResponseEntity deleteShoppingBasket(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_NUM_PATH_VAR_NAME) Integer shoppingBasketNum,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_BOOK_NUM_PATH_VAR_NAME) Integer shoppingBasketBooknum)
            throws EntityNotFoundException {

        /* Удаление данной позиции из корзины */
        shoppingBasketBookService.deleteShoppingBasketBook(customerId, shoppingBasketNum, shoppingBasketBooknum);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}