package com.mediasoft.bookstore.mapper.impl;

import com.mediasoft.bookstore.dto.CustomerDto;
import com.mediasoft.bookstore.entity.Customer;
import com.mediasoft.bookstore.mapper.CustomerMapper;
import com.mediasoft.bookstore.mapper.ShoppingBasketMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomerMapperImpl implements CustomerMapper {

    private final ShoppingBasketMapper shoppingBasketMapper;

    @Override
    public Customer toEntity(CustomerDto customerDto) {
        Customer customer;
        if (customerDto == null) {
            customer = null;
        } else {
            customer = new Customer();
            customer.setId(customerDto.getId());
            customer.setEmail(customerDto.getEmail());
            customer.setName(customerDto.getName());
            customer.setPhone(customerDto.getPhone());
            customer.setAddress(customerDto.getAddress());
            customer.setShoppingBaskets(
                    customerDto.getShoppingBasketDtos().stream()
                            .map(shoppingBasketMapper::toEntity)
                            .collect(Collectors.toList())
            );
        }
        return customer;
    }

    @Override
    public CustomerDto toDto(Customer customer) {
        CustomerDto customerDto;
        if (customer == null) {
            customerDto = null;
        } else {
            customerDto = new CustomerDto(
                    customer.getId(),
                    customer.getEmail(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getAddress(),
                    customer.getShoppingBaskets().stream()
                            .map(shoppingBasketMapper::toDto)
                            .collect(Collectors.toList())
            );
        }
        return customerDto;
    }
}
