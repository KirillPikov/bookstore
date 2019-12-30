package com.mediasoft.bookstore.mapper;

import com.mediasoft.bookstore.dto.CustomerDto;
import com.mediasoft.bookstore.entity.Customer;

public interface CustomerMapper {

    Customer toEntity(CustomerDto customerDto);

    CustomerDto toDto(Customer customer);
}
