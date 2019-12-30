package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.WarehouseDto;
import com.mediasoft.bookstore.entity.Warehouse;
import com.mediasoft.bookstore.mapper.WarehouseBookMapper;
import com.mediasoft.bookstore.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
            warehouse.setPhone(warehouseDto.getPhone());
            warehouse.setAddress(warehouseDto.getAddress());
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
                    warehouse.getPhone(),
                    warehouse.getAddress()
            );
        }
        return warehouseDto;
    }
}