package com.mediasoft.bookstore.service.implementation;

import com.mediasoft.bookstore.entity.Customer;
import com.mediasoft.bookstore.repository.CustomerRepository;
import com.mediasoft.bookstore.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(Long id) throws Exception {
        return customerRepository.findById(id).orElseThrow(Exception::new);
    }

    @Override
    public Customer addCustomer(Customer customer) throws Exception {
        if(customer.getId() == null || !customerRepository.existsById(customer.getId())) {
            customer = customerRepository.save(customer);
        } else {
            throw new Exception();
        }
        return customer;
    }

    @Override
    public Customer updateCustomer(Customer customer) throws Exception {
        customerRepository.findById(customer.getId())
                .ifPresentOrElse(repoCustomer -> {
                    repoCustomer = customer;
                    customerRepository.save(repoCustomer);
                }, Exception::new);
        return customerRepository.findById(customer.getId()).orElseThrow(Exception::new);
    }

    @Override
    public Boolean deleteCustomer(Customer customer) throws Exception {
        customerRepository.findById(customer.getId())
                .ifPresentOrElse(
                        repoCustomer -> {
                            repoCustomer = customer;
                            customerRepository.delete(repoCustomer);
                        }
                        , Exception::new);
        return true;
    }
}