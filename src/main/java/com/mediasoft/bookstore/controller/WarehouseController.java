package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.WarehouseDto;
import com.mediasoft.bookstore.entity.Warehouse;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.WarehouseMapper;
import com.mediasoft.bookstore.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping(PathSettings.WAREHOUSE_CONTROLLER_PATH)
public class WarehouseController {
    
    private final WarehouseService warehouseService;
    
    private final WarehouseMapper warehouseMapper;

    @GetMapping("/{" + PathSettings.WAREHOUSE_ID_PATH_VAR_NAME + "}")
    public ResponseEntity<WarehouseDto> getWarehouseById(@PathVariable(name = PathSettings.WAREHOUSE_ID_PATH_VAR_NAME) Long warehouseId)
            throws EntityNotFoundException {
        /* Получение склада из сервиса. */
        Warehouse warehouse = warehouseService.getWarehouse(warehouseId);
        /* Конвертирование в формат Dto */
        WarehouseDto warehouserDto = warehouseMapper.toDto(warehouse);
        return new ResponseEntity<>(warehouserDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addWarehouse(@RequestBody @Valid WarehouseDto warehouseDto) {
        /* Конвертирование в формат Entity */
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
        /* Добавление покупателя с помощью сервиса */
        warehouseService.addWarehouse(warehouse);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.WAREHOUSE_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updateWarehouse(@PathVariable(name = PathSettings.WAREHOUSE_ID_PATH_VAR_NAME) Long warehouseId,
                                         @RequestBody @Valid WarehouseDto warehouseDto)
            throws EntityNotFoundException {
        /* Конвертирование в формат Entity */
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDto);
        /* Обновление склада с помощью сервиса */
        warehouseService.updateWarehouse(warehouseId, warehouse);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.CUSTOMER_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deleteWarehouse(@PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long warehouseId)
            throws EntityNotFoundException {
        /* Удлаление склада с помощью сервиса */
        warehouseService.deleteWarehouse(warehouseId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}