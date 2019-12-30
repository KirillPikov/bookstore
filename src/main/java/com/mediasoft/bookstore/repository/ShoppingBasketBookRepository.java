package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingBasketBookRepository extends JpaRepository<ShoppingBasketBook, Long> {

    Boolean existsByShoppingBasket_IdAndBook_Id(Long shoppingBasketId, Long bookId);

    Optional<ShoppingBasketBook> getShoppingBasketBookByShoppingBasket_IdAndId(Long shoppingBasketId, Long shoppingBasketBookId);

    Optional<ShoppingBasketBook> getByIdAndShoppingBasket_IdAndBook_Id(Long shoppingBasketBookId, Long shoppingBasketId, Long bookId);
}