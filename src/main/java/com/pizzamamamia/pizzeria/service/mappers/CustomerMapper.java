package com.pizzamamamia.pizzeria.service.mappers;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper implements Mapper<Customer, CustomerDto> {
    @Override
    public CustomerDto toDto(Customer domain) {
        return new CustomerDto()
                .setId(domain.getId())
                .setEmail(domain.getEmail())
                .setFirstName(domain.getFirstName())
                .setLastName(domain.getLastName())
                .setPhone(domain.getPhone())
                .setAddressLine(domain.getAddressLine());
    }

    @Override
    public Customer toDomain(CustomerDto dto) {
        return new Customer()
                .setId(dto.getId())
                .setEmail(dto.getEmail())
                .setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setPhone(dto.getPhone())
                .setAddressLine(dto.getAddressLine());
    }
}
