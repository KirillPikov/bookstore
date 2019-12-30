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
     * @param shoppingBasketId ID корзины покупателя.
     * @return список позиций корзины.
     */
    List<ShoppingBasketBook> getShoppingBasketBookPage(Long customerId, Long shoppingBasketId);

    /**
     * Получение конкретной позиции из корзины.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины у покупателя.
     * @param shoppingBasketBookId ID позиции в корзине у покупателя.
     * @return позицию из корзины по её ID.
     * @throws EntityNotFoundException
     */
    ShoppingBasketBook getShoppingBasketBook(Long customerId, Long shoppingBasketId, Long shoppingBasketBookId)
            throws EntityNotFoundException;

    /**
     * Добавление новой позиции в корзину.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины у покупателя.
     * @param shoppingBasketBook новая позиция.
     * @throws EntityNotFoundException
     */
    void addShoppingBasketBook(Long customerId, Long shoppingBasketId, ShoppingBasketBook shoppingBasketBook);

    /**
     * Изменение позиции в корзине.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины у покупателя.
     * @param shoppingBasketBookId ID позиции в корзине у покупателя.
     * @param shoppingBasketBook новое состояние позиции.
     */
    void updateShoppingBasketBook(Long customerId, Long shoppingBasketId, Long shoppingBasketBookId, ShoppingBasketBook shoppingBasketBook);

    /**
     * Удаление конкретной позиции из корзины.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины у покупателя.
     * @param shoppingBasketBookId ID позиции в корзине у покупателя.
     * @throws EntityNotFoundException
     */
    void deleteShoppingBasketBook(Long customerId, Long shoppingBasketId, Long shoppingBasketBookId)
            throws EntityNotFoundException;
}
