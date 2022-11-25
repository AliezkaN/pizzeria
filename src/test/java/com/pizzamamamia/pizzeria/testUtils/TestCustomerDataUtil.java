package com.pizzamamamia.pizzeria.testUtils;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.model.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestCustomerDataUtil {

    public final static Long MOCK_ID = 1L;
    public final static String MOCK_EMAIL = "email@gmail.com";
    public final static String MOCK_FIRST_NAME = "Alex";
    public final static String MOCK_LAST_NAME = "Smith";
    public final static String MOCK_PHONE = "(066) 666-6666";
    public final static String MOCK_ADDRESS = "st. Horodotska, 38";

    public final static String MOCK_UPDATE_FIRST_NAME = "Bob";
    public final static String MOCK_UPDATE_LAST_NAME = "Tatum";
    public final static String MOCK_UPDATE_ADDRESS = "st. Horodotska, 222a";

    public static Customer createCustomer() {
        return Customer.builder()
                .firstName(MOCK_FIRST_NAME)
                .lastName(MOCK_LAST_NAME)
                .email(MOCK_EMAIL)
                .phone(MOCK_PHONE)
                .addressLine(MOCK_ADDRESS)
                .build();
    }

    public static CustomerDto createCustomerDto() {
        return CustomerDto.builder()
                .firstName(MOCK_FIRST_NAME)
                .lastName(MOCK_LAST_NAME)
                .email(MOCK_EMAIL)
                .phone(MOCK_PHONE)
                .addressLine(MOCK_ADDRESS)
                .build();
    }

}
