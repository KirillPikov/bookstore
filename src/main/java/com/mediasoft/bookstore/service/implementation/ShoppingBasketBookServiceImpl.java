package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import com.mediasoft.bookstore.entity.WarehouseBook;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.ShoppingBasketBookRepository;
import com.mediasoft.bookstore.repository.WarehouseBookRepository;
import com.mediasoft.bookstore.service.ShoppingBasketBookService;
import com.mediasoft.bookstore.service.ShoppingBasketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ShoppingBasketBookServiceImpl implements ShoppingBasketBookService {

    private final ShoppingBasketService shoppingBasketService;

    private final ShoppingBasketBookRepository shoppingBasketBookRepository;

    private final WarehouseBookRepository warehouseBookRepository;

    /**
     * Получение списка позиций конкретной корзины.
     *
     * @param customerId         ID покупателя.
     * @param shoppingBasketNum  номер корзины у покупателя.
     * @return список позиций корзины.
     */
    @Override
    public List<ShoppingBasketBook> getShoppingBasketBookPage(Long customerId, Integer shoppingBasketNum) {
        ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(customerId, shoppingBasketNum);
        return shoppingBasket.getShoppingBasketBooks();
    }

    /**
     * Получение конкретной позиции из корзины.
     *
     * @param customerId             ID покупателя.
     * @param shoppingBasketNum     номер корзины у покупателя.
     * @param shoppingBasketBookNum номер позиции в корзине у покупателя.
     * @return позицию из корзины по её номеру.
     * @throws EntityNotFoundException
     */
    @Override
    public ShoppingBasketBook getShoppingBasketBook(Long customerId,
                                                    Integer shoppingBasketNum,
                                                    Integer shoppingBasketBookNum)
            throws EntityNotFoundException {
        ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(customerId, shoppingBasketNum);
        Pageable pageable = PageRequest.of(shoppingBasketBookNum, 1);
        return shoppingBasketBookRepository.getShoppingBasketBookByShoppingBasket_Id(
                    shoppingBasket.getId(),
                    pageable)
                .get()
                .findFirst()
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException(
                                    "ShoppingBasketBook with customer_id = " + customerId +
                                    " shoppingBasketPage = " + shoppingBasketNum +
                                    " and shoppingBasketBookNum = " + shoppingBasketBookNum +
                                    " not found."
                            );
                        }
                );
    }

    /**
     * Добавление новой позиции в корзину.
     *
     * @param customerId         ID покупателя.
     * @param shoppingBasketNum номер корзины у покупателя.
     * @param shoppingBasketBook новая позиция.
     * @return новую позицию.
     */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ,
            propagation = Propagation.REQUIRED,
            rollbackFor = {EntityNotFoundException.class}
    )
    public void addShoppingBasketBook(Long customerId,
                                      Integer shoppingBasketNum,
                                      ShoppingBasketBook shoppingBasketBook) {
        shoppingBasketBook.setShoppingBasket(
                shoppingBasketService.getShoppingBasket(customerId, shoppingBasketNum)
        );
        /* Получаем позицию на складе, в котором хранится такая книга с количеством >= . */
        WarehouseBook warehouseBook = warehouseBookRepository.findByBook_IdAndCountGreaterThanEqual(
                shoppingBasketBook.getBook().getId(),
                shoppingBasketBook.getCount()
        ).orElseThrow(
                /* Если не нашли - бросаем исключение и транзакция откатывается. */
                () -> {
                    throw new EntityNotFoundException(
                            "WarehouseBook with bookId = " + shoppingBasketBook.getId() +
                                    " and count >= " + shoppingBasketBook.getCount() +
                                    " not found."
                    );
                }
        );
        /* Меняем количество книг на складе. */
        warehouseBook.setCount(
                warehouseBook.getCount() - shoppingBasketBook.getCount()
        );
        /* Сохраняем новые состояния. */
        warehouseBookRepository.save(warehouseBook);
        shoppingBasketBookRepository.save(shoppingBasketBook);
    }

    /**
     * Удаление конкретной позиции из корзины.
     *
     * @param customerId             ID покупателя.
     * @param shoppingBasketNum     номер корзины у покупателя.
     * @param shoppingBasketBookNum номер позиции в корзине у покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteShoppingBasketBook(Long customerId,
                                         Integer shoppingBasketNum,
                                         Integer shoppingBasketBookNum)
            throws EntityNotFoundException {
        shoppingBasketBookRepository.delete(
                this.getShoppingBasketBook(customerId, shoppingBasketNum, shoppingBasketBookNum)
        );
    }
}