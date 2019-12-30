package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PaginationSettings;
import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.ShoppingBasketDto;
import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.exception.ShoppingBasketReleaseException;
import com.mediasoft.bookstore.exception.ShoppingBasketUpdateException;
import com.mediasoft.bookstore.mapper.ShoppingBasketMapper;
import com.mediasoft.bookstore.service.ShoppingBasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping(PathSettings.SHOPPING_BASKET_CONTROLLER_PATH)
public class ShoppingBasketController {

    private final ShoppingBasketService shoppingBasketService;

    private final ShoppingBasketMapper shoppingBasketMapper;

    @GetMapping
    public ResponseEntity<List<ShoppingBasketDto>> getShoppingBasketId(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @RequestParam(name = PathSettings.PAGE_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_PAGE) Integer page,
            @RequestParam(name = PathSettings.COUNT_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_ELEMENTS_COUNT) Integer count,
            @RequestParam(name = PathSettings.SORTING_FIELD_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_SORTING_FIELD) String sortingField,
            @RequestParam(name = PathSettings.SORTING_REQUEST_PARAM, required = false, defaultValue = PaginationSettings.DEFAULT_SORTING) String sorting
    ) throws EntityNotFoundException {
        Sort sort;
        if(sorting.equals("ASC")) {
            sort = Sort.by(sortingField).ascending();
        } else {
            sort = Sort.by(sortingField).descending();
        }
        Pageable pageable = PageRequest.of(page, count, sort);
        /* Получение списка корзин потребителя */
        List<ShoppingBasketDto> shoppingBasketDtos = shoppingBasketService.getShoppingBasketsPage(customerId, pageable)
                .stream()
                /* Конвертирование в Dto */
                .map(shoppingBasketMapper::toDto)
                /* Сбор получившихся Dto в список */
                .collect(Collectors.toList());
        return new ResponseEntity<>(shoppingBasketDtos, HttpStatus.OK);
    }

    @GetMapping("/{" + PathSettings.SHOPPING_BASKET_ID_PATH_VAR_NAME + "}")
    public ResponseEntity<ShoppingBasketDto> getShoppingBasket(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_ID_PATH_VAR_NAME) Long shoppingBasketNum
    ) {
        /* Конвертирование в Dto */
        ShoppingBasketDto shoppingBasketDto = shoppingBasketMapper.toDto(
                /* Получение конкретной корзины с помощью сервиса */
                shoppingBasketService.getShoppingBasket(customerId, shoppingBasketNum)
        );
        return new ResponseEntity<>(shoppingBasketDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addShoppingBasket(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @RequestBody @Valid ShoppingBasketDto shoppingBasketDto
    ) {
        /* Конвертирование в Entity */
        ShoppingBasket shoppingBasket = shoppingBasketMapper.toEntity(shoppingBasketDto);
        /* Добавление новой корзины с помощью сервиса */
        shoppingBasketService.addShoppingBasket(customerId, shoppingBasket);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.SHOPPING_BASKET_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updateShoppingBasket(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_ID_PATH_VAR_NAME) Long shoppingBasketNum,
            @RequestBody @Valid ShoppingBasketDto shoppingBasketDto
    ) throws EntityNotFoundException, ShoppingBasketUpdateException, ShoppingBasketReleaseException {
        /* Конвертирование в формат Entity */
        ShoppingBasket shoppingBasket = shoppingBasketMapper.toEntity(shoppingBasketDto);
        /* Обновление корзины с помощью сервиса */
        shoppingBasketService.updateShoppingBasket(customerId, shoppingBasketNum, shoppingBasket);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.SHOPPING_BASKET_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deleteShoppingBasket(
            @PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
            @PathVariable(name = PathSettings.SHOPPING_BASKET_ID_PATH_VAR_NAME) Long shoppingBasketId
    ) throws EntityNotFoundException {
        shoppingBasketService.deleteShoppingBasket(customerId, shoppingBasketId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}