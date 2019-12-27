package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.ShoppingBasketRepository;
import com.mediasoft.bookstore.service.CustomerService;
import com.mediasoft.bookstore.service.ShoppingBasketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    private final CustomerService customerService;

    private final ShoppingBasketRepository shoppingBasketRepository;

    /**
     * Получение конкретной по номеру корзны покупателя.
     *
     * @param customerId         ID покупателя.
     * @param shoppingBasketNum номер корзины покупателя.
     * @return конкретную корзину покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public ShoppingBasket getShoppingBasket(Long customerId, Integer shoppingBasketNum) {
        /* Здесь Pageable для упрощённого смещения, т.е первый параметр указывает на то,
         сколько записий пропустить, а второй - сколько взять начиная со смещения */
        Pageable pageable = PageRequest.of(shoppingBasketNum, 1);
        /* Получаем конкретную корзину из репозитория */
        return shoppingBasketRepository.getShoppingBasketByCustomer_Id(customerId, pageable)
                .get()
                .findFirst()
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Shopping Basket with customer_id = " + customerId +
                            " and shoppingBasketNum = " + shoppingBasketNum +
                            " not found.");
                });
    }

    /**
     * Получение списка существующих корзин покупателя по его ID.
     *
     * @param customerId ID покупателя.
     * @param pageable настройки выходного списка.
     * @return список корзин покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public List<ShoppingBasket> getShoppingBasketsPage(Long customerId, Pageable pageable) throws EntityNotFoundException {
        return shoppingBasketRepository.getShoppingBasketsByCustomer_Id(customerId, pageable);
    }

    /**
     * Добавление покупателю новой корзины.
     *
     * @param customerId     ID покупателя.
     * @param shoppingBasket новая корзина.
     * @return новую корзину.
     */
    @Override
    public void addShoppingBasket(Long customerId, ShoppingBasket shoppingBasket) {
        shoppingBasket.setCustomer(
                customerService.getCustomer(customerId)
        );
        shoppingBasketRepository.save(shoppingBasket);
    }

    /**
     * Удаление конкретной корзины покупателя по её номеру.
     *
     * @param customerId          ID покупателя.
     * @param shoppingBasketNum номер корзины покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteShoppingBasket(Long customerId, Integer shoppingBasketNum)
            throws EntityNotFoundException {
        ShoppingBasket shoppingBasket = this.getShoppingBasket(customerId, shoppingBasketNum);
        shoppingBasketRepository.delete(shoppingBasket);
    }
}