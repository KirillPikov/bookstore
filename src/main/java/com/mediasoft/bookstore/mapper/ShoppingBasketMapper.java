package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.ShoppingBasketDto;
import com.mediasoft.bookstore.entity.ShoppingBasket;

public interface ShoppingBasketMapper {

    ShoppingBasket toEntity(ShoppingBasketDto shoppingBasketDto);

    ShoppingBasketDto toDto(ShoppingBasket shoppingBasket);
}
