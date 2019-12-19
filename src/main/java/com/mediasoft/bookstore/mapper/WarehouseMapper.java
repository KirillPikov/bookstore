package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.WarehouseDto;
import com.mediasoft.bookstore.entity.Warehouse;

public interface WarehouseMapper {

    Warehouse toEntity(WarehouseDto warehouseDto);

    WarehouseDto toDto(Warehouse warehouse);
}
