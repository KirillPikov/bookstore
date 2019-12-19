package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.ShoppingBasketBookDto;
import com.mediasoft.bookstore.entity.ShoppingBasketBook;

public interface ShoppingBasketBookMapper {

    ShoppingBasketBook toEntity(ShoppingBasketBookDto shoppingBasketBookDto);

    ShoppingBasketBookDto toDto(ShoppingBasketBook shoppingBasketBook);
}
