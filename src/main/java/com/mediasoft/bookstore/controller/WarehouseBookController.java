package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PaginationSettings;
import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.WarehouseBookDto;
import com.mediasoft.bookstore.entity.WarehouseBook;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.WarehouseBookMapper;
import com.mediasoft.bookstore.service.WarehouseBookService;
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
@RequestMapping(PathSettings.WAREHOUSE_BOOK_CONTROLLER_PATH)
public class WarehouseBookController {

    private final WarehouseBookService warehouseBookService;

    private final WarehouseBookMapper warehouseBookMapper;

    @GetMapping
    public ResponseEntity<List<WarehouseBookDto>> getWarehouseBooksPageByWarehouseId(
            @PathVariable(name = PathSettings.WAREHOUSE_ID_PATH_VAR_NAME) Long warehouseId,
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
        /* Получение страницы всех позиций конкретного склада */
        List<WarehouseBookDto> warehouseBookDtos = warehouseBookService.getWarehouseBooksPageByWarehouseId(warehouseId, pageable)
                .stream()
                /* Конвертирование в Dto */
                .map(warehouseBookMapper::toDto)
                /* Сбор получившихся Dto в список */
                .collect(Collectors.toList());
        return new ResponseEntity<>(warehouseBookDtos, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addWarehouseBook(
            @PathVariable(name = PathSettings.WAREHOUSE_ID_PATH_VAR_NAME) Long warehouseId,
            @RequestBody @Valid WarehouseBookDto warehouseBookDto
    ) {
        /* Конвертирование в Entity */
        WarehouseBook warehouseBook = warehouseBookMapper.toEntity(warehouseBookDto);
        /* Добавление новой позиции с помощью сервиса */
        warehouseBookService.addWarehouseBook(warehouseId, warehouseBook);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.WAREHOUSE_BOOK_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updateWarehouseBook(
            @PathVariable(name = PathSettings.WAREHOUSE_ID_PATH_VAR_NAME) Long warehouseId,
            @PathVariable(name = PathSettings.WAREHOUSE_BOOK_ID_PATH_VAR_NAME) Long warehouseBookId,
            @RequestBody @Valid WarehouseBookDto warehouseBookDto
    ) throws EntityNotFoundException {
        /* Конвертирование в формат Entity */
        WarehouseBook warehouseBook = warehouseBookMapper.toEntity(warehouseBookDto);
        /* Обновление позиции на складе с помощью сервиса */
        warehouseBookService.updateWarehouseBook(warehouseId, warehouseBookId, warehouseBook);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.WAREHOUSE_BOOK_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deleteShoppingBasket(
            @PathVariable(name = PathSettings.WAREHOUSE_ID_PATH_VAR_NAME) Long warehouseId,
            @PathVariable(name = PathSettings.WAREHOUSE_BOOK_ID_PATH_VAR_NAME) Long warehouseBookId
    ) throws EntityNotFoundException {
        warehouseBookService.deleteWarehouseBook(warehouseId, warehouseBookId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}