package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoppingBasketRepository extends JpaRepository<ShoppingBasket, Long> {
    /**
     * Необходим для получение конкретной корзины по ID, и которая принадледит покупателю с конкретным ID
     *
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины.
     * @return
     */
    Optional<ShoppingBasket> getShoppingBasketByCustomer_IdAndId(Long customerId, Long shoppingBasketId);

    /**
     * Получение списка корзин у конкретного покупателя.
     *
     * @param customerId ID покупателя.
     * @param pageable информация о странице с корзинами.
     * @return
     */
    List<ShoppingBasket> getShoppingBasketsByCustomer_Id(Long customerId, Pageable pageable);
}
