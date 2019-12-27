package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketBookRepository extends JpaRepository<ShoppingBasketBook, Long> {

    Page<ShoppingBasketBook> getShoppingBasketBookByShoppingBasket_Id(Long shoppingBasketId, Pageable pageable);
}