package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.model.Customer;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> listCustomers();
    CustomerDto getCustomer(String email);
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(String email, CustomerDto customer);
    void deleteCustomer(String email);
}
