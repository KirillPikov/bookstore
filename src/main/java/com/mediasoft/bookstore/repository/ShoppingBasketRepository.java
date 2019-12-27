package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {
    /**
     * Необходим для получение одной конкретной корзины по её номеру, а не ID, который передаётся в pageable
     *
     * @param customerId ID покупателя.
     * @param pageable информация о номере корзины.
     * @return
     */
    Page<ShoppingBasket> getShoppingBasketByCustomer_Id(Long customerId, Pageable pageable);

    /**
     * Получение списка корзин у конкретного покупателя.
     *
     * @param customerId ID покупателя.
     * @param pageable информация о странице с корзинами.
     * @return
     */
    List<ShoppingBasket> getShoppingBasketsByCustomer_Id(Long customerId, Pageable pageable);
}
