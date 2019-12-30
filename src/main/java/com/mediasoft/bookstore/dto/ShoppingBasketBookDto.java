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
public final class ShoppingBasketBookDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long shoppingBasketId;

    @NotNull(message = "Поле book должно быть задано.")
    @JsonProperty(value = "book")
    @JsonDeserialize(converter = BookIdToBookDtoConverter.class)
    private final BookDto bookDto;

    @NotNull(message = "Поле count должно быть задано.")
    @Range(min = 1, message = "Количество книг в позиции не может быть меньше 1.")
    private final Integer count;
}