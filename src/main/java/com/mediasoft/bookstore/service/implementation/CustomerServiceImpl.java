package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.Customer;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.repository.CustomerRepository;
import com.mediasoft.bookstore.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    /**
     * Получение поткупателя по ID.
     *
     * @param customerId ID покупаетля, которого хотим получить.
     * @return покупателя с указанным ID.
     * @throws EntityNotFoundException
     */
    @Override
    public Customer getCustomer(Long customerId) throws EntityNotFoundException {
        /* Пробуем найти покупателя по переданному ID */
        return customerRepository.findById(customerId)
                /* В случае отсутствия такого бросаем исключение, иначе возвращаем найденное */
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("Customer with id = " + customerId + " - not found.");
                        }
                );
    }

    /**
     * Добавление нового покупателя.
     *
     * @param customer новый покупатель.
     * @return созданный покупатель.
     */
    @Override
    public void addCustomer(Customer customer) {
        /* Сохранение нового переданного покупателя */
        customerRepository.save(customer);
    }

    /**
     * Обновление существующего покупателя.
     *
     * @param customerId ID покупателя, которого хотим обносить.
     * @param customer   новое состояние покупателя.
     * @throws EntityNotFoundException
     */
    @Override
    public void updateCustomer(Long customerId, Customer customer) throws EntityNotFoundException {
        /* Выставляю ID покупателю, так как изначальное он не проинициализирован. */
        customer.setId(customerId);
        /* Пробуем найти покупателя по переданному ID */
        customerRepository.findById(customerId)
                /* Если получилось найти, меняем его состояние и сохраняем */
                .ifPresentOrElse(
                        repoCustomer -> {
                            repoCustomer = customer;
                            customerRepository.save(repoCustomer);
                        }, () -> {
                                /* Иначе выбрасываем исключение */
                                throw new EntityNotFoundException("Customer with id = " + customer.getId() + " - not found.");
                            }
                );
    }

    /**
     * Удаление покупателя по ID.
     *
     * @param customerId ID покупателя, которого хотим удалить.
     * @throws EntityNotFoundException
     */
    @Override
    public void deleteCustomer(Long customerId) throws EntityNotFoundException {
        /* Проверяем существование покупателя с таким ID */
        if(customerRepository.existsById(customerId)) {
            /* Если существует - удаляем */
            customerRepository.deleteById(customerId);
        } else {
            /* Иначе выбрасываем исключение */
            throw new EntityNotFoundException("Customer with id = " + customerId + " - not found.");
        }
    }
}