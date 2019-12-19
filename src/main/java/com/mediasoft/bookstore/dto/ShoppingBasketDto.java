package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ShoppingBasketDto {

    private final Long id;

    @NotNull
    private final Long customerId;

    private final List<ShoppingBasketBookDto> shoppingBasketBookDtos;
}
