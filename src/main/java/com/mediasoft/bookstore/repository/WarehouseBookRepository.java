package com.mediasoft.bookstore.repository;

import com.mediasoft.bookstore.entity.WarehouseBook;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WarehouseBookRepository extends JpaRepository<WarehouseBook, Long> {

    /**
     * Получение всех позиций на складах, где есть такая книга.
     * @param bookId
     * @return
     */
    List<WarehouseBook> getAllByBook_Id(Long bookId);

    /**
     * Получение всех позиций конкретного склада по его ID.
     * @param warehouseId ID склада.
     * @param pageable параметры страницы.
     * @return
     */
    List<WarehouseBook> getAllByWarehouse_Id(Long warehouseId, Pageable pageable);

    /**
     * Проверят существование на складе с данным ID позиции с данным ID.
     * @param warehouseBookId ID позиции.
     * @param warehouseId ID склада.
     * @return
     */
    Optional<WarehouseBook> findByIdAndWarehouse_Id(Long warehouseBookId, Long warehouseId);

    /**
     * Находит позицию на складе, на котором хранится данная книга в количестве больше или равно.
     * @param bookId
     * @param count
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    Optional<WarehouseBook> findByBook_IdAndCountGreaterThanEqual(Long bookId, Integer count);
}