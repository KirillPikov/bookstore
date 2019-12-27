package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public final class WarehouseBookDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull
    private final Long warehouseId;

    @NotNull
    @JsonProperty(value = "book")
    private final BookDto bookDto;

    @NotNull
    @Range(max = 100, message = "Количество должно быть неотрицательным и меньше 100.")
    private final Integer count;
}
