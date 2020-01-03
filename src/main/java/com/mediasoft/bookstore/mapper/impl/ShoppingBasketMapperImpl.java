package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.ShoppingBasketDto;
import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.mapper.ShoppingBasketBookMapper;
import com.mediasoft.bookstore.mapper.ShoppingBasketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ShoppingBasketMapperImpl implements ShoppingBasketMapper {

    private final ShoppingBasketBookMapper shoppingBasketBookMapper;

    @Override
    public ShoppingBasket toEntity(ShoppingBasketDto shoppingBasketDto) {
        ShoppingBasket shoppingBasket;
        if (shoppingBasketDto == null) {
            shoppingBasket = null;
        } else {
            shoppingBasket = new ShoppingBasket();
            shoppingBasket.setShoppingBasketBooks(
                    Objects.nonNull(shoppingBasket.getShoppingBasketBooks()) ?
                    shoppingBasketDto.getShoppingBasketBookDtos().stream()
                            .map(shoppingBasketBookMapper::toEntity)
                            .collect(Collectors.toList())
                    : null
            );
            shoppingBasket.setShoppingBasketStatus(
                    shoppingBasketDto.getShoppingBasketStatus()
            );
        }
        return shoppingBasket;
    }

    @Override
    public ShoppingBasketDto toDto(ShoppingBasket shoppingBasket) {
        ShoppingBasketDto shoppingBasketDto;
        if (shoppingBasket == null) {
            shoppingBasketDto = null;
        } else {
            shoppingBasketDto = new ShoppingBasketDto(
                    shoppingBasket.getId(),
                    shoppingBasket.getCustomer().getId(),
                    Objects.nonNull(shoppingBasket.getShoppingBasketBooks()) ?
                    shoppingBasket.getShoppingBasketBooks().stream()
                            .map(shoppingBasketBookMapper::toDto)
                            .collect(Collectors.toList())
                    : null,
                    shoppingBasket.getShoppingBasketStatus()
            );
        }
        return shoppingBasketDto;
    }
}