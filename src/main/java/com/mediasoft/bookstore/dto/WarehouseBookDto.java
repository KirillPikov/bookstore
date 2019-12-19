package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class WarehouseBookDto {

    private final Long id;

    @NotNull
    private final Long warehouseId;

    @NotNull
    private final BookDto bookDto;

    @NotNull
    @Size(message = "Количество должно быть неотрицательным.")
    private final Integer count;
}
