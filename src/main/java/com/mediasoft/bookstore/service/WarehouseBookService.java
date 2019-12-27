package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.WarehouseBook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseBookService {
    /**
     * Получение страницы позиций всех складов.
     * @param pageable настройки страницы.
     * @return все позиции конкретного склада.
     */
    List<WarehouseBook> getWarehouseBooksPageByWarehouseId(Pageable pageable);

    /**
     * Получение страницы позиций конкретного склада.
     * @param warehouseId ID склада.
     * @param pageable настройки страницы.
     * @return все позиции конкретного склада.
     */
    List<WarehouseBook> getWarehouseBooksPageByWarehouseId(Long warehouseId, Pageable pageable);

    /**
     * Добавление новой позиции на конкретный склад.
     * @param warehouseId ID склада.
     * @param warehouseBook новая позиция.
     */
    void addWarehouseBook(Long warehouseId, WarehouseBook warehouseBook);

    /**
     * Изменение состояния позиции.
     * @param warehouseId ID склада.
     * @param warehouseBookId ID позиции на складе.
     * @param warehouseBook новая позиция.
     */
    void updateWarehouseBook(Long warehouseId, Long warehouseBookId, WarehouseBook warehouseBook);

    /**
     * Удаление позиции по ID.
     * @param warehouseId ID склада.
     * @param warehouseBookId ID позиции.
     */
    void deleteWarehouseBook(Long warehouseId, Long warehouseBookId);
}