package com.mediasoft.bookstore.controller;

import com.mediasoft.bookstore.dto.CustomerDto;
import com.mediasoft.bookstore.entity.Customer;
import com.mediasoft.bookstore.mapper.CustomerMapper;
import com.mediasoft.bookstore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerController(CustomerService customerService, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.customerMapper = customerMapper;
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerDto> getUserById(@PathVariable Long id) throws Exception {
        Customer customer = customerService.getCustomer(id);
        CustomerDto customerDto = customerMapper.toDto(customer);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody @Valid CustomerDto customerDto) throws Exception {
        Customer customer = customerMapper.toEntity(customerDto);
        customerDto = customerMapper.toDto(
                customerService.addCustomer(customer)
        );
        ResponseEntity<CustomerDto> response = new ResponseEntity(customerDto, HttpStatus.OK);
        return response;
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody @Valid CustomerDto customerDto) throws Exception {
        Customer customer = customerMapper.toEntity(customerDto);
        customerDto = customerMapper.toDto(
                customerService.updateCustomer(customer)
        );
        ResponseEntity<CustomerDto> response = new ResponseEntity(customerDto, HttpStatus.OK);
        return response;
    }

    @DeleteMapping(consumes = "application/json")
    public Boolean deleteCustomer(@RequestBody CustomerDto customerDto) throws Exception {
        Customer customer = customerMapper.toEntity(customerDto);
        Boolean deleted = customerService.deleteCustomer(customer);
        return deleted;
    }
}
