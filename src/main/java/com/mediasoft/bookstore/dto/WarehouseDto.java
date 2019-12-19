package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class WarehouseDto {

    private final Long id;

    private final List<WarehouseBookDto> warehouseBookDtos;
}
