package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import com.mediasoft.bookstore.entity.WarehouseBook;
import com.mediasoft.bookstore.entity.enums.ShoppingBasketStatus;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.exception.ShoppingBasketReleaseException;
import com.mediasoft.bookstore.exception.ShoppingBasketUpdateException;
import com.mediasoft.bookstore.repository.ShoppingBasketRepository;
import com.mediasoft.bookstore.repository.WarehouseBookRepository;
import com.mediasoft.bookstore.service.CustomerService;
import com.mediasoft.bookstore.service.ShoppingBasketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ShoppingBasketServiceImpl implements ShoppingBasketService {

    private final CustomerService customerService;

    private final ShoppingBasketRepository shoppingBasketRepository;

    private final WarehouseBookRepository warehouseBookRepository;

    /**
     * Получение конкретной по ID корзны покупателя.
     *
     * @param customerId         ID покупателя.
     * @param shoppingBasketId   ID корзины покупателя.
     * @return конкретную корзину покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public ShoppingBasket getShoppingBasket(Long customerId, Long shoppingBasketId) throws EntityNotFoundException {
        /* Получаем конкретную корзину из репозитория */
        return shoppingBasketRepository.getShoppingBasketByCustomer_IdAndId(customerId, shoppingBasketId)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException("Shopping Basket with customer_id = " + customerId +
                            " and shoppingBasketId = " + shoppingBasketId +
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
     */
    @Override
    public void addShoppingBasket(Long customerId, ShoppingBasket shoppingBasket) {
        shoppingBasket.setCustomer(
                customerService.getCustomer(customerId)
        );
        shoppingBasketRepository.save(shoppingBasket);
    }

    /**
     * Изменение состояния корзины покупателя.
     *
     * @param customerId        ID покупателя.
     * @param shoppingBasketId  ID корзины.
     * @param shoppingBasket    корзина с новым состоянием.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {EntityNotFoundException.class}
    )
    public void updateShoppingBasket(Long customerId, Long shoppingBasketId, ShoppingBasket shoppingBasket)
        throws EntityNotFoundException, ShoppingBasketUpdateException, ShoppingBasketReleaseException {
        /* Чтобы сохранить новое состояние корзины, нам надо знать её ID,
         поэтому сначала получаем корзину по имеющимся у нас данным. */
        ShoppingBasket shoppingBasketFromRepo = this.getShoppingBasket(customerId, shoppingBasketId);
        /* В случае, если корзина имеет статус отличнй от ACTIVE - кидаем исключение. */
        if(!shoppingBasketFromRepo.getShoppingBasketStatus().equals(ShoppingBasketStatus.ACTIVE)) {
            throw new ShoppingBasketUpdateException(
                    "Can't update shoppingBasket because status = " + shoppingBasketFromRepo.getShoppingBasketStatus() +
                    ". You can update shoppingBasket only with " + ShoppingBasketStatus.ACTIVE + " status."
            );
        }
        /* Если корзина переводится в статус ORDERD, и её старый статус был ACTIVE, необходимо списать со склада заказанные книги. */
        if(shoppingBasket.getShoppingBasketStatus().equals(ShoppingBasketStatus.ORDERED)) {
            this.releaseShoppingBasket(shoppingBasketFromRepo);
        }
        shoppingBasketFromRepo.setShoppingBasketStatus(
                shoppingBasket.getShoppingBasketStatus()
        );
        shoppingBasketFromRepo.setShoppingBasketBooks(
                shoppingBasket.getShoppingBasketBooks()
        );
    }

    /**
     * Метод списывающий заказанные книги со складов.
     * @param shoppingBasket корзина с заказанными книгами.
     */
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {EntityNotFoundException.class}
    )
    protected void releaseShoppingBasket(ShoppingBasket shoppingBasket)
            throws ShoppingBasketReleaseException {
        /* Корзина должна быть непустая. */
        if(shoppingBasket.getShoppingBasketBooks().isEmpty()) {
            throw new ShoppingBasketReleaseException(
                    "Can't release shoppingBasket because it have no one positions."
            );
        }
        for (ShoppingBasketBook shoppingBasketBook : shoppingBasket.getShoppingBasketBooks()) {
            /* Получаем позицию на складе, в котором хранится такая книга с количеством >= . */
            WarehouseBook warehouseBook = warehouseBookRepository.findByBook_IdAndCountGreaterThanEqual(
                    shoppingBasketBook.getBook().getId(),
                    shoppingBasketBook.getCount()
            ).orElseThrow(
                    /* Если не нашли - бросаем исключение и транзакция откатывается. */
                    () -> {
                        throw new ShoppingBasketReleaseException(
                                "Can't release shoppingBasket because" +
                                        " warehouseBook with bookId = " + shoppingBasketBook.getId() +
                                        " and count >= " + shoppingBasketBook.getCount() +
                                        " not found."
                        );
                    }
            );
            /* Меняем количество книг на складе. */
            warehouseBook.setCount(
                    warehouseBook.getCount() - shoppingBasketBook.getCount()
            );
        }
    }

    /**
     * Удаление конкретной корзины покупателя по её ID.
     *
     * @param customerId          ID покупателя.
     * @param shoppingBasketId ID корзины покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteShoppingBasket(Long customerId, Long shoppingBasketId)
            throws EntityNotFoundException {
        ShoppingBasket shoppingBasket = this.getShoppingBasket(customerId, shoppingBasketId);
        shoppingBasketRepository.delete(shoppingBasket);
    }
}