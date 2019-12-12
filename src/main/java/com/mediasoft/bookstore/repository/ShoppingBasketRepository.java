package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingBasketRepository extends CrudRepository<ShoppingBasket, Long> {
}
