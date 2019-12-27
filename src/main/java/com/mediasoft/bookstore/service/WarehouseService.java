package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.Warehouse;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WarehouseService {
    /**
     * Получение склада по ID.
     * @param warehouseId ID покупаетля, которого хотим получить.
     * @return склад с указанным ID.
     * @throws EntityNotFoundException
     */
    Warehouse getWarehouse(Long warehouseId) throws EntityNotFoundException;

    /** //TODO доделать!
     * Получние списка всех складов.
     * @return
     */
    List<Warehouse> getAllWarehouses();

    /**
     * Добавление нового склада.
     * @param warehouse новый склад.
     */
    void addWarehouse(Warehouse warehouse);

    /**
     * Обновление существующего склада.
     * @param warehouseId ID склада, который хотим обновить.
     * @param warehouse новое состояние склада.
     * @throws EntityNotFoundException
     */
    void updateWarehouse(Long warehouseId, Warehouse warehouse) throws EntityNotFoundException;

    /**
     * Удаление склада по ID.
     * @param warehouseId ID склада, который хотим удалить.
     * @throws EntityNotFoundException
     */
    void deleteWarehouse(Long warehouseId) throws EntityNotFoundException;
}