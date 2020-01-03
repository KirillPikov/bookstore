package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.Customer;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    /**
     * Получение поткупателя по ID.
     * @param customerId ID покупаетля, которого хотим получить.
     * @return покупателя с указанным ID.
     * @throws EntityNotFoundException
     */
    Customer getCustomer(Long customerId) throws EntityNotFoundException;

    /**
     * Добавление нового покупателя.
     * @param customer новй покупатель.
     */
    void addCustomer(Customer customer);

    /**
     * Обновление существующего покупателя.
     * @param customerId ID покупателя, которого хотим обносить.
     * @param customer новое состояние покупателя.
     * @throws EntityNotFoundException
     */
    void updateCustomer(Long customerId, Customer customer) throws EntityNotFoundException;

    /**
     * Удаление покупателя по ID.
     * @param customerId ID покупателя, которого хотим удалить.
     * @throws EntityNotFoundException
     */
    void deleteCustomer(Long customerId) throws EntityNotFoundException;
}