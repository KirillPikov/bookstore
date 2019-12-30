package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.ShoppingBasket;
import com.mediasoft.bookstore.entity.ShoppingBasketBook;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.ShoppingBasketBookRepository;
import com.mediasoft.bookstore.service.ShoppingBasketBookService;
import com.mediasoft.bookstore.service.ShoppingBasketService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ShoppingBasketBookServiceImpl implements ShoppingBasketBookService {

    private final ShoppingBasketService shoppingBasketService;

    private final ShoppingBasketBookRepository shoppingBasketBookRepository;

    /**
     * Получение списка позиций конкретной корзины.
     *
     * @param customerId         ID покупателя.
     * @param shoppingBasketId   ID корзины у покупателя.
     * @return список позиций корзины.
     */
    @Override
    public List<ShoppingBasketBook> getShoppingBasketBookPage(Long customerId, Long shoppingBasketId) {
        ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(customerId, shoppingBasketId);
        return shoppingBasket.getShoppingBasketBooks();
    }

    /**
     * Получение конкретной позиции из корзины.
     *
     * @param customerId             ID покупателя.
     * @param shoppingBasketId     ID корзины у покупателя.
     * @param shoppingBasketBookId ID позиции в корзине у покупателя.
     * @return позицию из корзины по её IDу.
     * @throws EntityNotFoundException
     */
    @Override
    public ShoppingBasketBook getShoppingBasketBook(Long customerId,
                                                    Long shoppingBasketId,
                                                    Long shoppingBasketBookId
    ) throws EntityNotFoundException {
        ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(customerId, shoppingBasketId);
        return shoppingBasketBookRepository.getShoppingBasketBookByShoppingBasket_IdAndId(shoppingBasket.getId(), shoppingBasketBookId)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException(
                                    "ShoppingBasketBook with customer_id = " + customerId +
                                    " shoppingBasketPage = " + shoppingBasketId +
                                    " and shoppingBasketBookId = " + shoppingBasketBookId +
                                    " not found."
                            );
                        }
                );
    }

    /**
     * Добавление новой позиции в корзину.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины у покупателя.
     * @param shoppingBasketBook новая позиция.
     * @throws EntityNotFoundException
     */
    public void addShoppingBasketBook(
            Long customerId,
            Long shoppingBasketId,
            ShoppingBasketBook shoppingBasketBook
    ) {
        /* Получение экземпляра корзины необходимо для проверки существования позиции
         с такой корзиной и с такой книгой, как в переданной позиции */
        ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(customerId, shoppingBasketId);
        /* Проверка существования. */
        if(shoppingBasketBookRepository.existsByShoppingBasket_IdAndBook_Id(shoppingBasket.getId(), shoppingBasketBook.getBook().getId())) {
            /* То есть у нас есть позиция в корзине, в которой уже есть такая книга. Вызываем метод обновления. */
            this.updateShoppingBasketBook(customerId, shoppingBasketId, shoppingBasketId, shoppingBasketBook);
        } else {
            /* Если не нашли такую позицию, значит надо создать новую. */
            shoppingBasketBookRepository.save(shoppingBasketBook);
        }
    }

    /**
     * Изменение позиции в корзине.
     * @param customerId ID покупателя.
     * @param shoppingBasketId ID корзины у покупателя.
     * @param shoppingBasketBookId ID позиции в корзине у покупателя.
     * @param shoppingBasketBook новое состояние позиции.
     */
    public void updateShoppingBasketBook(Long customerId,
                                         Long shoppingBasketId,
                                         Long shoppingBasketBookId,
                                         ShoppingBasketBook shoppingBasketBook) {
        /* Находим корзину с переданными параметрами. */
        ShoppingBasket shoppingBasket = shoppingBasketService.getShoppingBasket(customerId, shoppingBasketId);
        /* Затем пробуем найти позицию, для которой существует такая корзина с такой книгой */
        shoppingBasketBookRepository.getByIdAndShoppingBasket_IdAndBook_Id(
                shoppingBasketBookId,
                shoppingBasket.getId(),
                shoppingBasketBook.getBook().getId()
        ).ifPresentOrElse(
                /* В случае, если такую нашли - меняем её состояние. */
                shoppingBasketBookRepo -> {
                    shoppingBasketBook.setId(
                            shoppingBasketBookRepo.getId()
                    );
                    shoppingBasketBookRepo = shoppingBasketBook;
                    shoppingBasketBookRepository.save(shoppingBasketBookRepo);
                }, () -> {
                        /* Иначе выбрасываем исключение */
                        throw new EntityNotFoundException(
                                "ShoppingBasketBook with customerId = " + customerId +
                                " shoppingBasketId = " + shoppingBasketId +
                                " shoppingBasketBookId = " + shoppingBasketBookId +
                                " - not found.");
                    }
        );
    }

    /**
     * Удаление конкретной позиции из корзины.
     *
     * @param customerId             ID покупателя.
     * @param shoppingBasketId     ID корзины у покупателя.
     * @param shoppingBasketBookId ID позиции в корзине у покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteShoppingBasketBook(Long customerId,
                                         Long shoppingBasketId,
                                         Long shoppingBasketBookId)
            throws EntityNotFoundException {
        shoppingBasketBookRepository.delete(
                this.getShoppingBasketBook(customerId, shoppingBasketId, shoppingBasketBookId)
        );
    }
}