package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingBasketBookRepository extends JpaRepository<ShoppingBasketBook, Long> {

    /**
     * Проверяет существование позиции в данной корзине с данной книгой.
     * @param shoppingBasketId ID корзины.
     * @param bookId ID книги.
     * @return
     */
    Boolean existsByShoppingBasket_IdAndBook_Id(Long shoppingBasketId, Long bookId);

    /**
     * Получение позиции из конкретной корзины с конкретной книгой.
     * @param shoppingBasketId  ID корзины.
     * @param bookId ID книги.
     * @return
     */
    Optional<ShoppingBasketBook> getByShoppingBasketIdAndBookId(Long shoppingBasketId, Long bookId);

    /**
     * Получение позиции по её ID и ID корзины.
     * @param shoppingBasketId ID корзины.
     * @param shoppingBasketBookId ID позиции.
     * @return
     */
    Optional<ShoppingBasketBook> getShoppingBasketBookByShoppingBasket_IdAndId(Long shoppingBasketId, Long shoppingBasketBookId);

    /**
     * Получение позиции по её ID, ID корзины и ID книги.
     * @param shoppingBasketBookId ID позиции.
     * @param shoppingBasketId ID корзины.
     * @param bookId ID книги.
     * @return
     */
    Optional<ShoppingBasketBook> getByIdAndShoppingBasket_IdAndBook_Id(Long shoppingBasketBookId, Long shoppingBasketId, Long bookId);
}