package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.ShoppingBasketBookDto;
import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import com.mediasoft.bookstore.mapper.BookMapper;
import com.mediasoft.bookstore.mapper.ShoppingBasketBookMapper;
import com.mediasoft.bookstore.repository.ShoppingBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ShoppingBasketBookMapperImpl implements ShoppingBasketBookMapper {

    private final ShoppingBasketRepository shoppingBasketRepository;

    private final BookMapper bookMapper;

    @Override
    public ShoppingBasketBook toEntity
            (ShoppingBasketBookDto shoppingBasketBookDto) {
        ShoppingBasketBook shoppingBasketBook;
        if (shoppingBasketBookDto == null) {
            shoppingBasketBook = null;
        } else {
            shoppingBasketBook = new ShoppingBasketBook();
            shoppingBasketBook.setId(shoppingBasketBookDto.getId());
            shoppingBasketBook.setShoppingBasket(
                    shoppingBasketRepository.findById(
                            shoppingBasketBookDto.getShoppingBasketId()).orElse(null)  //TODO мб тут в сервисе получать, где будет обработка ошибок
            );
            shoppingBasketBook.setBook(
                    bookMapper.toEntity(
                            shoppingBasketBookDto.getBookDto()
                    )
            );
            shoppingBasketBook.setCount(shoppingBasketBookDto.getCount());
        }
        return shoppingBasketBook;
    }

    @Override
    public ShoppingBasketBookDto toDto
            (ShoppingBasketBook shoppingBasketBook) {
        ShoppingBasketBookDto shoppingBasketBookDto;
        if (shoppingBasketBook == null) {
            shoppingBasketBookDto = null;
        } else {
            shoppingBasketBookDto = new ShoppingBasketBookDto(
                    shoppingBasketBook.getId(),
                    shoppingBasketBook.getShoppingBasket().getId(),
                    bookMapper.toDto(
                            shoppingBasketBook.getBook()
                    ),
                    shoppingBasketBook.getCount()
            );
        }
        return shoppingBasketBookDto;
    }
}
