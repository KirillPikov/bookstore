package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.config.PathSettings;
import com.mediasoft.bookstore.dto.CustomerDto;
import com.mediasoft.bookstore.entity.Customer;
import com.mediasoft.bookstore.exception.EntityNotFoundException;
import com.mediasoft.bookstore.mapper.CustomerMapper;
import com.mediasoft.bookstore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RequestMapping(PathSettings.CUSTOMER_CONTROLLER_PATH)
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    @GetMapping("/{" + PathSettings.CUSTOMER_ID_PATH_VAR_NAME + "}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId)
            throws EntityNotFoundException {
        /* Получение из покупателя из сервиса. */
        Customer customer = customerService.getCustomer(customerId);
        /* Конвертирование в формат Dto */
        CustomerDto customerDto = customerMapper.toDto(customer);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity addCustomer(@RequestBody @Valid CustomerDto customerDto) {
        /* Конвертирование в формат Entity */
        Customer customer = customerMapper.toEntity(customerDto);
        /* Конвертирование в формат Dto */
        /* Добавление покупателя с помощью сервиса */
        customerService.addCustomer(customer);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{" + PathSettings.CUSTOMER_ID_PATH_VAR_NAME + "}", consumes = "application/json")
    public ResponseEntity updateCustomer(@PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId,
                                         @RequestBody @Valid CustomerDto customerDto)
            throws EntityNotFoundException {
        /* Конвертирование в формат Entity */
        Customer customer = customerMapper.toEntity(customerDto);
        /* Обновление покупателя с помощью сервиса */
        customerService.updateCustomer(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{" + PathSettings.CUSTOMER_ID_PATH_VAR_NAME + "}")
    public ResponseEntity deleteCustomer(@PathVariable(name = PathSettings.CUSTOMER_ID_PATH_VAR_NAME) Long customerId)
            throws EntityNotFoundException {
        /* Удлаление покупателя с помощью сервиса */
        customerService.deleteCustomer(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}