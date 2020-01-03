package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.exception.ShoppingBasketUpdateException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShoppingBasketService {

    /**
     * Получение конкретной корзины по её ID и ID покупателя.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины.
     * @return конкретную корзину покупателя.
     * @throws EntityNotFoundException
     */
    ShoppingBasket getShoppingBasket(Long customerId, Long shoppingBasketId) throws EntityNotFoundException;

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
     * Изменение состояния корзины покупателя.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины.
     * @param shoppingBasket корзина с новым состоянием.
     * @throws ShoppingBasketUpdateException возникает при смене статуса корзины, причём её текущий (старый) отличен от ACTIVE
     */
    void updateShoppingBasket(Long customerId, Long shoppingBasketId, ShoppingBasket shoppingBasket) throws EntityNotFoundException, ShoppingBasketUpdateException;

    /**
     * Удаление конкретной корзины покупателя по её IDу.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины покупателя.
     * @throws EntityNotFoundException
     */
    void deleteShoppingBasket(Long customerId, Long shoppingBasketId) throws EntityNotFoundException;
}