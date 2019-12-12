package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketBookRepository extends CrudRepository<ShoppingBasketBook, Long> {
}
