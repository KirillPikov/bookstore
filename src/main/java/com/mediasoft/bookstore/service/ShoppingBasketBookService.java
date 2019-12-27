package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingBasketBookService {
    /**
     * Получение списка позиций конкретной корзины.
     * @param customerId ID покупателя.
     * @param shoppingBasketNum номер корзины покупателя.
     * @return список позиций корзины.
     */
    List<ShoppingBasketBook> getShoppingBasketBookPage(Long customerId, Integer shoppingBasketNum);

    /**
     * Получение конкретной позиции из корзины.
     * @param customerId ID покупателя.
     * @param shoppingBasketNum номер корзины у покупателя.
     * @param shoppingBasketBookNum номер позиции в корзине у покупателя.
     * @return позицию из корзины по её номеру.
     * @throws EntityNotFoundException
     */
    ShoppingBasketBook getShoppingBasketBook(Long customerId, Integer shoppingBasketNum, Integer shoppingBasketBookNum)
            throws EntityNotFoundException;

    /**
     * Добавление новой позиции в корзину.
     * @param customerId ID покупателя.
     * @param shoppingBasketNum номер корзины у покупателя.
     * @param shoppingBasketBook новая позиция.
     */
    void addShoppingBasketBook(Long customerId, Integer shoppingBasketNum, ShoppingBasketBook shoppingBasketBook);

    /**
     * Удаление конкретной позиции из корзины.
     * @param customerId ID покупателя.
     * @param shoppingBasketNum номер корзины у покупателя.
     * @param shoppingBasketBookNum номер позиции в корзине у покупателя.
     * @throws EntityNotFoundException
     */
    void deleteShoppingBasketBook(Long customerId, Integer shoppingBasketNum, Integer shoppingBasketBookNum)
            throws EntityNotFoundException;
}
