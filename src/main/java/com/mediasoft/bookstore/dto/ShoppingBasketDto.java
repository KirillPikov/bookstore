package com.mediasoft.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mediasoft.bookstore.entity.enums.ShoppingBasketStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor(onConstructor = @__({@JsonCreator}))
public final class ShoppingBasketDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final Long id;

    @JsonIgnore
    private final Long customerId;

    @JsonProperty(value = "shoppingBasketBooks", access = JsonProperty.Access.READ_ONLY)
    private final List<ShoppingBasketBookDto> shoppingBasketBookDtos;

    @JsonProperty(value = "status")
    private final ShoppingBasketStatus shoppingBasketStatus;
}