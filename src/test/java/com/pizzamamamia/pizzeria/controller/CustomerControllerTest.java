package com.pizzamamamia.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.exception.ErrorType;
import com.pizzamamamia.pizzeria.model.Status;
import com.pizzamamamia.pizzeria.service.CustomerService;
import com.pizzamamamia.pizzeria.service.OrderService;
import com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestOrderDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = CustomerController.class)
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;
    @MockBean
    private OrderService orderService;

    private static final String MAPPING = "/api/v1/customer/";

    @Test
    void getAllCustomersTest() throws Exception {

        CustomerDto expectedCustomer = TestCustomerDataUtil.createCustomerDto();
        when(customerService.listCustomers()).thenReturn(Collections.singletonList(expectedCustomer));

        mockMvc.perform(get(MAPPING))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName").value(expectedCustomer.getFirstName()))
                .andExpect(jsonPath("$[0].email").value(expectedCustomer.getEmail()));
    }

    @Test
    void getCustomerTest() throws Exception {
        CustomerDto expectedCustomer = TestCustomerDataUtil.createCustomerDto();
        when(customerService.getCustomer(MOCK_EMAIL)).thenReturn(expectedCustomer);

        mockMvc.perform(get(MAPPING + MOCK_EMAIL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expectedCustomer.getFirstName()))
                .andExpect(jsonPath("$.email").value(expectedCustomer.getEmail()));
    }

    @Test
    void createValidCustomerTest() throws Exception {

        CustomerDto createBody = TestCustomerDataUtil.createCustomerDto();
        CustomerDto expectedUser = TestCustomerDataUtil.createCustomerDto();
        when(customerService.createCustomer(createBody)).thenReturn(expectedUser);

        mockMvc.perform(
                        post(MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(expectedUser.getFirstName()))
                .andExpect(jsonPath("$.email").value(expectedUser.getEmail()));
    }

    @Test
    void createInvalidCustomerTest() throws Exception {
        CustomerDto createBody = TestCustomerDataUtil.createCustomerDto();
        createBody.setEmail("not valid email");
        createBody.setPhone("not valid phone");
        when(customerService.createCustomer(createBody)).thenReturn(createBody);

        mockMvc.perform(
                        post(MAPPING)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void updateCustomerWithValidBodyTest() throws Exception {

        CustomerDto updateBody = TestCustomerDataUtil.createUpdatedCustomerDto();
        CustomerDto expectedUser = TestCustomerDataUtil.createUpdatedCustomerDto();
        when(customerService.updateCustomer(MOCK_EMAIL,updateBody)).thenReturn(expectedUser);

        mockMvc.perform(
                        patch(MAPPING + MOCK_EMAIL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(MOCK_UPDATE_FIRST_NAME))
                .andExpect(jsonPath("$.lastName").value(MOCK_UPDATE_LAST_NAME))
                .andExpect(jsonPath("$.addressLine").value(MOCK_UPDATE_ADDRESS));
    }

    @Test
    void updateCustomerWithInvalidBodyTest() throws Exception {
        CustomerDto updateBody = TestCustomerDataUtil.createCustomerDto();
        CustomerDto expectedUser = TestCustomerDataUtil.createUpdatedCustomerDto();
        when(customerService.updateCustomer(MOCK_EMAIL,updateBody)).thenReturn(expectedUser);

        mockMvc.perform(
                        patch(MAPPING + MOCK_EMAIL)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void deleteCustomerTest() throws Exception {
        doNothing().when(customerService).deleteCustomer(MOCK_EMAIL);

        mockMvc
                .perform(delete(MAPPING + MOCK_EMAIL))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void createOrderTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        when(orderService.createOrder(MOCK_EMAIL,MOCK_ID)).thenReturn(expectedOrder);

        mockMvc.perform(
                post(MAPPING + MOCK_EMAIL + "/createOrder/" + MOCK_ID))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.CREATED)));
    }

    @Test
    void showCartTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        expectedOrder.setStatus(Status.CARTED);
        when(orderService.getCartedOrders(MOCK_EMAIL)).thenReturn(Collections.singletonList(expectedOrder));

        mockMvc.perform(get(MAPPING + MOCK_EMAIL +"/showCart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value(String.valueOf(Status.CARTED)));
    }

    @Test
    void showOrderHistoryTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        expectedOrder.setStatus(Status.CONFIRMED);
        when(orderService.getOrders(MOCK_EMAIL)).thenReturn(Collections.singletonList(expectedOrder));

        mockMvc.perform(get(MAPPING + MOCK_EMAIL +"/showOrderHistory"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value(String.valueOf(expectedOrder.getStatus())));
    }

    @Test
    void getCreatedOrdersTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        when(orderService.getCreatedOrders(MOCK_EMAIL)).thenReturn(Collections.singletonList(expectedOrder));

        mockMvc.perform(get(MAPPING + MOCK_EMAIL +"/getCreatedOrders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].status").value(String.valueOf(expectedOrder.getStatus())));
    }
}
