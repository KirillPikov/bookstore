package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.WarehouseDto;
import com.mediasoft.bookstore.entity.Warehouse;
import com.mediasoft.bookstore.mapper.WarehouseBookMapper;
import com.mediasoft.bookstore.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WarehouseMapperImpl implements WarehouseMapper {

    private final WarehouseBookMapper warehouseBookMapper;

    @Override
    public Warehouse toEntity(WarehouseDto warehouseDto) {
        Warehouse warehouse;
        if (warehouseDto == null) {
            warehouse = null;
        } else {
            warehouse = new Warehouse();
            warehouse.setId(warehouseDto.getId());
            warehouse.setWarehouseBook(
                    warehouseDto.getWarehouseBookDtos().stream()
                            .map(warehouseBookMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }
        return warehouse;
    }

    @Override
    public WarehouseDto toDto(Warehouse warehouse) {
        WarehouseDto warehouseDto;
        if (warehouse == null) {
            warehouseDto = null;
        } else {
            warehouseDto = new WarehouseDto(
                    warehouse.getId(),
                    warehouse.getWarehouseBook().stream()
                            .map(warehouseBookMapper::toDto)
                            .collect(Collectors.toList())
            );
        }
        return warehouseDto;
    }
}
