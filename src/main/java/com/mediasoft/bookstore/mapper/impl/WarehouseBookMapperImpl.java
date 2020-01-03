package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.WarehouseBookDto;
import com.mediasoft.bookstore.entity.WarehouseBook;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.mapper.WarehouseBookMapper;
import com.mediasoft.bookstore.repository.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class WarehouseBookMapperImpl implements WarehouseBookMapper {

    private final WarehouseRepository warehouseRepository;

    private final BookMapper bookMapper;

    @Override
    public WarehouseBook toEntity(WarehouseBookDto warehouseBookDto) {
        WarehouseBook warehouseBook;
        if (warehouseBookDto == null) {
            warehouseBook = null;
        } else {
            warehouseBook = new WarehouseBook();
            warehouseBook.setId(warehouseBookDto.getId());
            warehouseBook.setWarehouse(
                    warehouseRepository.findById(
                            warehouseBookDto.getWarehouseId()
                    )
                    .orElse(null)
            );
            warehouseBook.setBook(
                    bookMapper.toEntity(
                            warehouseBookDto.getBookDto()
                    )
            );
            warehouseBook.setCount(warehouseBookDto.getCount());
        }
        return warehouseBook;
    }

    @Override
    public WarehouseBookDto toDto(WarehouseBook warehouseBook) {
        WarehouseBookDto warehouseBookDto;
        if (warehouseBook == null) {
            warehouseBookDto = null;
        } else {
            warehouseBookDto = new WarehouseBookDto(
                    warehouseBook.getId(),
                    warehouseBook.getWarehouse().getId(),
                    bookMapper.toDto(warehouseBook.getBook()),
                    warehouseBook.getCount()
            );
        }
        return warehouseBookDto;
    }
}