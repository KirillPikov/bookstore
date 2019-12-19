package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ShoppingBasketBookDto {

    private final Long id;

    @NotNull    //TODO Мессаджи!
    private final Long shoppingBasketId;

    @NotNull
    private final BookDto bookDto;

    @NotNull
    @Size(min = 1, message = "Количество книг в корзине не может быть меньше 1.")
    private final Integer count;
}
