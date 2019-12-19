package com.mediasoft.bookstore.service;

import com.mediasoft.bookstore.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

    Customer getCustomer(Long id) throws Exception;

    Customer addCustomer(Customer customerDto) throws Exception;

    Customer updateCustomer(Customer customerDto) throws Exception;

    Boolean deleteCustomer(Customer customerDto) throws Exception;
}
