package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.mediasoft.bookstore.dto.converter.BookIdToBookDtoConverter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public final class WarehouseBookDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @NotNull(message = "Поле warehouseId должно быть задано.")
    private final Long warehouseId;

    @JsonProperty(value = "book")
    @NotNull(message = "Поле book должно быть задано.")
    @JsonDeserialize(converter = BookIdToBookDtoConverter.class)
    private final BookDto bookDto;

    @NotNull(message = "Поле count должно быть задано.")
    @Range(max = 100, message = "Количество должно быть неотрицательным и меньше 100.")
    private final Integer count;
}