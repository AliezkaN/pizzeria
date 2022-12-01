package com.pizzamamamia.pizzeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.model.Status;
import com.pizzamamamia.pizzeria.service.IngredientService;
import com.pizzamamamia.pizzeria.service.OrderService;
import com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestOrderDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.MOCK_ID;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    private static final String MAPPING = "/api/v1/orders/";

    @Test
    void addToppingToPizzaTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        expectedOrder.getToppings().add(TestIngredientDataUtil.createIngredientDto());
        when(orderService.addTopping(MOCK_ID,MOCK_ID)).thenReturn(expectedOrder);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/addTopping/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.CREATED)))
                .andExpect(jsonPath("$.toppings.size()").value(1));
    }

    @Test
    void deleteToppingFromPizzaTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        expectedOrder.getToppings().clear();
        when(orderService.deleteTopping(MOCK_ID,MOCK_ID)).thenReturn(expectedOrder);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/deleteTopping/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.CREATED)))
                .andExpect(jsonPath("$.toppings.size()").value(0));
    }

    @Test
    void addOrderToCartTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        expectedOrder.setStatus(Status.CARTED);
        when(orderService.addOrderToCart(MOCK_ID)).thenReturn(expectedOrder);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/addOrderToCart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.CARTED)));
    }

    @Test
    void deleteOrderFromCartTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        when(orderService.deleteOrderFromCart(MOCK_ID)).thenReturn(expectedOrder);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/deleteOrderFromCart"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.CREATED)));
    }

    @Test
    void confirmOrderTest() throws Exception {
        OrderDto expectedOrder = TestOrderDataUtil.createOrderDto();
        expectedOrder.setStatus(Status.CONFIRMED);
        when(orderService.confirmOrder(MOCK_ID)).thenReturn(expectedOrder);

        mockMvc.perform(patch(MAPPING + MOCK_ID + "/confirmOrder"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(String.valueOf(Status.CONFIRMED)));
    }
}
