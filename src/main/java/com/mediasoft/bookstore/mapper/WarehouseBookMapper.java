package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.WarehouseBookDto;
import com.mediasoft.bookstore.entity.WarehouseBook;

public interface WarehouseBookMapper {

    WarehouseBook toEntity(WarehouseBookDto warehouseBookDto);

    WarehouseBookDto toDto(WarehouseBook warehouseBook);
}
