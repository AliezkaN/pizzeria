package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.exception.CustomerNotFoundException;
import com.pizzamamamia.pizzeria.exception.SuchCustomerAlreadyExistException;
import com.pizzamamamia.pizzeria.model.Customer;
import com.pizzamamamia.pizzeria.repository.CustomerRepository;
import com.pizzamamamia.pizzeria.service.impl.CustomerServiceImpl;
import com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void listCustomersTest(){
        //given
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        List<Customer> customerList = Collections.singletonList(expectedCustomer);
        when(customerRepository.findAll()).thenReturn(customerList);

        //when
        List<CustomerDto> customers = customerService.listCustomers();

        //then
        assertThat(customers, hasSize(1));
    }

    @Test
    void getCustomerByEmailTest(){
        //given
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));

        //when
        CustomerDto actualCustomer = customerService.getCustomer(MOCK_EMAIL);

        //then
        assertEquals(expectedCustomer.getEmail(),actualCustomer.getEmail());
        assertThat(actualCustomer,allOf(
                hasProperty("firstName",equalTo(expectedCustomer.getFirstName())),
                hasProperty("lastName",equalTo(expectedCustomer.getLastName()))
        ));
    }

    @Test
    void createCustomerTest(){
        //given
        CustomerDto createBody = TestCustomerDataUtil.createCustomerDto();
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        when(customerRepository.save(any())).thenReturn(expectedCustomer);

        //when
        createBody = customerService.createCustomer(createBody);

        //then
        assertThat(createBody,allOf(
                hasProperty("firstName",equalTo(expectedCustomer.getFirstName())),
                hasProperty("lastName",equalTo(expectedCustomer.getLastName()))
        ));

    }

    @Test
    void deleteCustomerTest(){
        //given
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        doNothing().when(customerRepository).delete(any());

        //when
        customerService.deleteCustomer(MOCK_EMAIL);

        //then
        verify(customerRepository, times(1)).delete(expectedCustomer);
    }

    @Test
    void updateCustomerTest(){
        //given
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        CustomerDto updateBody = CustomerDto.builder()
                                            .firstName(MOCK_UPDATE_FIRST_NAME)
                                            .lastName(MOCK_UPDATE_LAST_NAME)
                                            .addressLine(MOCK_UPDATE_ADDRESS)
                                            .build();
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        when(customerRepository.save(any())).thenReturn(expectedCustomer);

        //when
        updateBody = customerService.updateCustomer(MOCK_EMAIL,updateBody);

        assertThat(updateBody, allOf(
                hasProperty("firstName",equalTo(expectedCustomer.getFirstName())),
                hasProperty("lastName",equalTo(expectedCustomer.getLastName())),
                hasProperty("email",equalTo(expectedCustomer.getEmail()))
        ));
    }

    @Test
    void createCustomerWithEmailThatExistsTest(){
        CustomerDto createBody = TestCustomerDataUtil.createCustomerDto();
        when(customerRepository.existsByEmail(MOCK_EMAIL)).thenReturn(Boolean.TRUE);
        assertThrows(SuchCustomerAlreadyExistException.class , () -> customerService.createCustomer(createBody));
    }

    @Test
    void createCustomerWithPhoneThatExistsTest(){
        CustomerDto createBody = TestCustomerDataUtil.createCustomerDto();
        when(customerRepository.existsByPhone(TestCustomerDataUtil.MOCK_PHONE)).thenReturn(Boolean.TRUE);
        assertThrows(SuchCustomerAlreadyExistException.class , () -> customerService.createCustomer(createBody));
    }

    @Test
    void getCustomerByEmailNotFoundTest(){
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(MOCK_EMAIL));
    }

    @Test
    void deleteCustomerNotFoundTest(){
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(MOCK_EMAIL));
    }


}
