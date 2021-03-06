package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.WarehouseBook;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.WarehouseBookRepository;
import com.mediasoft.bookstore.service.WarehouseBookService;
import com.mediasoft.bookstore.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class WarehouseBookServiceImpl implements WarehouseBookService {

    private final WarehouseBookRepository warehouseBookRepository;

    private final WarehouseService warehouseService;

    /**
     * Получение страницы позиций конкретного склада.
     *
     * @param warehouseId ID склада.
     * @param pageable настройки страницы.
     * @return все позиции конкретного склада.
     */
    @Override
    public List<WarehouseBook> getWarehouseBooksPageByWarehouseId(Long warehouseId, Pageable pageable) {
        return warehouseBookRepository.getAllByWarehouse_Id(warehouseId, pageable);
    }

    /**
     * Добавление новой позиции на конкретный склад.
     *
     * @param warehouseId   ID склада.
     * @param warehouseBook новая позиция.
     * @return созданную позицию.
     */
    @Override
    public void addWarehouseBook(Long warehouseId, WarehouseBook warehouseBook) {
        /* При добавлении новой позиции осуществляется проверка на существование
         на данном складе данной книги в каком-либо количестве данной книги, если есть такая позиция,
         то мы обновляем количество книг на этом складе в этой позиции, иначе добавляем новую. */
        warehouseBook.setWarehouse(
                warehouseService.getWarehouse(warehouseId)
        );
        if(warehouseBookRepository.existsByWarehouse_IdAndBook_Id(warehouseId, warehouseBook.getBook().getId())) {
            warehouseBookRepository.findByWarehouse_IdAndBook_Id(
                    warehouseId,
                    warehouseBook.getBook().getId()
            ).ifPresent(warehouseBookRepo -> {
                this.updateWarehouseBook(warehouseId, warehouseBookRepo.getId(), warehouseBook);
            });
        } else {
            warehouseBookRepository.save(warehouseBook);
        }
    }

    /**
     * Изменение состояния позиции.
     *
     * @param warehouseId     ID склада.
     * @param warehouseBookId ID позиции на складе.
     * @param warehouseBook   новая позиция.
     */
    @Override
    public void updateWarehouseBook(Long warehouseId, Long warehouseBookId, WarehouseBook warehouseBook) {
        /* Выставляю ID позиции, так как изначальное он не проинициализирован. */
        warehouseBook.setId(warehouseBookId);
        /* Пробуем найти позицию по переданному ID позиции и ID склада */
        warehouseBookRepository.findByIdAndWarehouse_Id(warehouseBookId, warehouseId)
                /* Если получилось найти, меняем её состояние и сохраняем */
                .ifPresentOrElse(
                        repoWarehouseBook -> {
                            repoWarehouseBook = warehouseBook;
                            warehouseBookRepository.save(repoWarehouseBook);
                        }, () -> {
                            /* Иначе выбрасываем исключение */
                            throw new EntityNotFoundException(
                                    "WarehouseBook with id = " + warehouseBookId +
                                    " and warehouseId = " + warehouseId +
                                    " - not found."
                            );
                        }
                );
    }

    /**
     * Удаление позиции по ID.
     *
     * @param warehouseId ID склада.
     * @param warehouseBookId ID позиции.
     */
    @Override
    public void deleteWarehouseBook(Long warehouseId, Long warehouseBookId) {
        warehouseBookRepository.findByIdAndWarehouse_Id(warehouseBookId, warehouseId)
                /* Если получилось найти, удаляем */
                .ifPresentOrElse(
                        warehouseBook -> warehouseBookRepository.deleteById(warehouseBookId)
                        , () -> {
                            /* Иначе выбрасываем исключение */
                            throw new EntityNotFoundException(
                                    "WarehouseBook with id = " + warehouseBookId +
                                            " and warehouseId = " + warehouseId +
                                            " - not found."
                            );
                        }
                );
    }
}