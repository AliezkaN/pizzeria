package com.pizzamamamia.pizzeria.service.impl;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.exception.CustomerNotFoundException;
import com.pizzamamamia.pizzeria.exception.SuchCustomerAlreadyExistException;
import com.pizzamamamia.pizzeria.model.Customer;
import com.pizzamamamia.pizzeria.repository.CustomerRepository;
import com.pizzamamamia.pizzeria.service.CustomerService;
import com.pizzamamamia.pizzeria.service.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final Mapper<Customer, CustomerDto> mapper;

    @Override
    public List<CustomerDto> listCustomers() {
        log.info("CustomerService --> get all customers");
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomer(String email) {
        log.info("CustomerService --> getCustomer by email{}", email);
        Customer customer = repository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        return mapper.toDto(customer);
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        log.info("CustomerService --> createCustomer {}", customerDto);
        if (repository.existsByEmail(customerDto.getEmail())) {
            throw new SuchCustomerAlreadyExistException("Customer with such email already exists");
        }
        if (repository.existsByPhone(customerDto.getPhone())) {
            throw new SuchCustomerAlreadyExistException("Customer with such phone already exists");
        }
        Customer customer = mapper.toDomain(customerDto);
        customer = repository.save(customer);
        return mapper.toDto(customer);
    }

    @Override
    public CustomerDto updateCustomer(String email, CustomerDto customerDto) {
        log.info("CustomerService --> updateCustomer with email {}", email);
        Customer persistedCustomer = repository.findByEmail(email)
                .orElseThrow(CustomerNotFoundException::new);

        final String firstName = customerDto.getFirstName();
        if (Objects.nonNull(firstName)) {
            persistedCustomer.setFirstName(firstName);
        }

        final String lastName = customerDto.getLastName();
        if (Objects.nonNull(lastName)) {
            persistedCustomer.setLastName(lastName);
        }

        final String addressLine = customerDto.getAddressLine();
        if (Objects.nonNull(addressLine)) {
            persistedCustomer.setAddressLine(addressLine);
        }

        Customer storedCustomer = repository.save(persistedCustomer);
        log.info("CustomerService --> Customer with email {} successfully updated", storedCustomer.getEmail());
        return mapper.toDto(storedCustomer);
    }

    @Override
    public void deleteCustomer(String email) {
        log.info("CustomerService --> deleteCustomer with email {}", email);
        Customer customer = repository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        repository.delete(customer);
    }
}
