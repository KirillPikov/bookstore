package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingBasketService {
    /**
     * Получение конкретной корзины по её номеру и ID покупателя.
     * @param customerId ID покупателя.
     * @param shoppingBasketNum номер корзины.
     * @return конкретную корзину покупателя.
     * @throws EntityNotFoundException
     */
    ShoppingBasket getShoppingBasket(Long customerId, Integer shoppingBasketNum) throws EntityNotFoundException;

    /**
     * Получение всех корзин покупателя постранично.
     * @param customerId ID покупателя.
     * @param pageable
     * @return конкретную корзину покупателя.
     * @throws EntityNotFoundException
     */
    List<ShoppingBasket> getShoppingBasketsPage(Long customerId, Pageable pageable) throws EntityNotFoundException;

    /**
     * Добавление покупателю новой корзины.
     * @param customerId ID покупателя.
     * @param shoppingBasket новая корзина.
     */
    void addShoppingBasket(Long customerId, ShoppingBasket shoppingBasket);

    /**
     * Удаление конкретной корзины покупателя по её номеру.
     * @param customerId ID покупателя.
     * @param shoppingBasketNum номер корзины покупателя.
     * @throws EntityNotFoundException
     */
    void deleteShoppingBasket(Long customerId, Integer shoppingBasketNum) throws EntityNotFoundException;
}