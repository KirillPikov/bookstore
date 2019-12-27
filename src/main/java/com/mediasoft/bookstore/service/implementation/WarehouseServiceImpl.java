package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.Warehouse;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.WarehouseRepository;
import com.mediasoft.bookstore.service.WarehouseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class WarehouseServiceImpl implements WarehouseService {

    private final WarehouseRepository warehouseRepository;

    /**
     * Получение склада по ID.
     *
     * @param warehouseId ID покупаетля, которого хотим получить.
     * @return склад с указанным ID.
     * @throws EntityNotFoundException
     */
    @Override
    public Warehouse getWarehouse(Long warehouseId) throws EntityNotFoundException {
        /* Пробуем найти скоад по переданному ID */
        return warehouseRepository.findById(warehouseId)
                /* В случае отсутствия такого бросаем исключение, иначе возвращаем найденное */
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Warehouse with id = " + warehouseId + " - not found.");
                        }
                );
    }

    /**
     * Получние списка всех складов.
     *
     * @return список всех складов
     */
    @Override
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    /**
     * Добавление нового склада.
     *
     * @param warehouse новый склад.
     * @return созданный склад.
     */
    @Override
    public void addWarehouse(Warehouse warehouse) {
        /* Сохранение нового переданного склада */
        warehouseRepository.save(warehouse);
    }

    /**
     * Обновление существующего склада.
     *
     * @param warehouseId ID склада, которого хотим обносить.
     * @param warehouse   новое состояние склада.
     * @throws EntityNotFoundException
     */
    @Override
    public void updateWarehouse(Long warehouseId, Warehouse warehouse) throws EntityNotFoundException {
        /* Выставляю ID складу, так как изначальное он не проинициализирован. */
        warehouse.setId(warehouseId);
        /* Пробуем найти склад по переданному ID */
        warehouseRepository.findById(warehouseId)
                /* Если получилось найти, меняем его состояние и сохраняем */
                .ifPresentOrElse(
                        repoCustomer -> {
                            repoCustomer = warehouse;
                            warehouseRepository.save(repoCustomer);
                        }, () -> {
                            /* Иначе выбрасываем исключение */
                            throw new EntityNotFoundException("Warehouse with id = " + warehouse.getId() + " - not found.");
                        }
                );
    }

    /**
     * Удаление склада по ID.
     *
     * @param warehouseId ID склада, которого хотим удалить.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteWarehouse(Long warehouseId) throws EntityNotFoundException {
        /* Проверяем существование склада с таким ID */
        if(warehouseRepository.existsById(warehouseId)) {
            /* Если существует - удаляем */
            warehouseRepository.deleteById(warehouseId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Warehouse with id = " + warehouseId + " - not found.");
        }
    }
}