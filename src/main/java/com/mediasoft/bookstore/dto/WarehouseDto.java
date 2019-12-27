package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public final class WarehouseDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    private final String phone;

    private final String address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final List<WarehouseBookDto> warehouseBookDtos;
}
