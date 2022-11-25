package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.exception.IngredientNotFoundException;
import com.pizzamamamia.pizzeria.exception.OrderCreationException;
import com.pizzamamamia.pizzeria.exception.ServiceException;
import com.pizzamamamia.pizzeria.model.*;
import com.pizzamamamia.pizzeria.repository.CustomerRepository;
import com.pizzamamamia.pizzeria.repository.IngredientRepository;
import com.pizzamamamia.pizzeria.repository.OrderRepository;
import com.pizzamamamia.pizzeria.repository.PizzaRepository;
import com.pizzamamamia.pizzeria.service.impl.OrderServiceImpl;
import com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestOrderDataUtil;
import com.pizzamamamia.pizzeria.testUtils.TestPizzaDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pizzamamamia.pizzeria.testUtils.TestCustomerDataUtil.MOCK_EMAIL;
import static com.pizzamamamia.pizzeria.testUtils.TestIngredientDataUtil.MOCK_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PizzaRepository pizzaRepository;
    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    void createOrderTest(){
        //given
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        expectedPizza.getIngredients().add(TestIngredientDataUtil.createIngredient());
        Order expectedOrder = TestOrderDataUtil.createOrder();
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));
        when(orderRepository.save(any())).thenReturn(expectedOrder);

        //when
        OrderDto orderDto = orderService.createOrder(MOCK_EMAIL,MOCK_ID);
        System.out.println(orderDto);
        //then
        assertThat(orderDto,allOf(
                hasProperty("price",equalTo(expectedPizza.getPrice()))
        ));
    }

    @Test
    void getOrdersTest(){
        //given
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        Order expectedOrder = TestOrderDataUtil.createOrder();
        List<Order> orderList = Collections.singletonList(expectedOrder);
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        when(orderRepository.findAllByCustomer(expectedCustomer)).thenReturn(orderList);

        //when
        List<OrderDto> orders = orderService.getOrders(MOCK_EMAIL);

        //then
        assertThat(orders, hasSize(1));
        assertEquals(orders.get(0).getPizza(),expectedOrder.getPizza());
    }

    @Test
    void getCreatedOrdersTest(){
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        Order expectedOrder = TestOrderDataUtil.createOrder();
        List<Order> orderList = Collections.singletonList(expectedOrder);
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        when(orderRepository.findAllByCustomerAndStatus(expectedCustomer, Status.CREATED)).thenReturn(orderList);

        //when
        List<OrderDto> orders = orderService.getCreatedOrders(MOCK_EMAIL);

        //then
        assertThat(orders, hasSize(1));
        OrderDto actualOrder = orders.get(0);
        assertEquals(actualOrder.getPizza(),expectedOrder.getPizza());
        assertEquals(actualOrder.getStatus(),Status.CREATED);
    }

    @Test
    void getCartedOrdersTest(){
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CARTED);
        List<Order> orderList = Collections.singletonList(expectedOrder);
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        when(orderRepository.findAllByCustomerAndStatus(expectedCustomer, Status.CARTED)).thenReturn(orderList);

        //when
        List<OrderDto> orders = orderService.getCartedOrders(MOCK_EMAIL);

        //then
        assertThat(orders, hasSize(1));
        assertEquals(orders.get(0).getStatus(),Status.CARTED);
    }

    @Test
    void addTopping(){
        //given
        Order expectedOrder = TestOrderDataUtil.createOrder();
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        when(orderRepository.save(any())).thenReturn(expectedOrder);

        //when
        OrderDto orderDto = orderService.addTopping(MOCK_ID,MOCK_ID);

        //then
        assertThat(orderDto.getToppings(), hasSize(1));
    }

    @Test
    void deleteToppingTest(){
        //given
        Order expectedOrder = TestOrderDataUtil.createOrder();
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        when(orderRepository.save(any())).thenReturn(expectedOrder);

        //when
        OrderDto orderDto = orderService.deleteTopping(MOCK_ID,MOCK_ID);

        //then
        assertThat(orderDto.getToppings(), hasSize(0));
    }

    @Test
    void addOrderToCartTest(){
        //given
        Order expectedOrder = TestOrderDataUtil.createOrder();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(orderRepository.save(any())).thenReturn(expectedOrder);

        //when
        OrderDto actualOrder = orderService.addOrderToCart(MOCK_ID);

        assertThat(actualOrder.getStatus(),equalTo(Status.CARTED));
    }

    @Test
    void deleteOrderFromCartTest(){
        //given
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CARTED);
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(orderRepository.save(any())).thenReturn(expectedOrder);

        //when
        OrderDto actualOrder = orderService.deleteOrderFromCart(MOCK_ID);

        assertThat(actualOrder.getStatus(),equalTo(Status.CREATED));
    }

    @Test
    void confirmOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CARTED);
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(orderRepository.save(any())).thenReturn(expectedOrder);

        //when
        OrderDto actualOrder = orderService.confirmOrder(MOCK_ID);

        assertThat(actualOrder.getStatus(),equalTo(Status.CONFIRMED));
    }

    @Test
    void createOrderWithEmptyPizzaTest(){
        Customer expectedCustomer = TestCustomerDataUtil.createCustomer();
        Pizza expectedPizza = TestPizzaDataUtil.createPizza();
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.getPizza().getIngredients().clear();
        when(customerRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedCustomer));
        when(pizzaRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedPizza));
        assertThrows(OrderCreationException.class, () -> orderService.createOrder(MOCK_EMAIL,MOCK_ID));
    }

    @Test
    void addToppingToCartedOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CARTED);
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        assertThrows(OrderCreationException.class, () -> orderService.addTopping(MOCK_ID,MOCK_ID));
    }

    @Test
    void addToppingToConfirmedOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CONFIRMED);
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        assertThrows(OrderCreationException.class, () -> orderService.addTopping(MOCK_ID,MOCK_ID));
    }

    @Test
    void deleteToppingToCartedOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CARTED);
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        assertThrows(OrderCreationException.class, () -> orderService.deleteTopping(MOCK_ID,MOCK_ID));
    }

    @Test
    void deleteToppingToConfirmedOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CONFIRMED);
        Ingredient expectedIngredient = TestIngredientDataUtil.createIngredient();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        when(ingredientRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedIngredient));
        assertThrows(OrderCreationException.class, () -> orderService.deleteTopping(MOCK_ID,MOCK_ID));
    }

    @Test
    void addOrderToCartAlreadyCartedTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CARTED);
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        assertThrows(OrderCreationException.class, () -> orderService.addOrderToCart(MOCK_ID));
    }

    @Test
    void addOrderToCartAlreadyConfirmedTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CONFIRMED);
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        assertThrows(OrderCreationException.class, () -> orderService.addOrderToCart(MOCK_ID));
    }

    @Test
    void deleteOrderFromCartCreatedOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        assertThrows(OrderCreationException.class, () -> orderService.deleteOrderFromCart(MOCK_ID));
    }

    @Test
    void deleteOrderFromCartAlreadyConfirmedTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CONFIRMED);
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        assertThrows(OrderCreationException.class, () -> orderService.deleteOrderFromCart(MOCK_ID));
    }

    @Test
    void confirmOrderCreatedOrderTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        assertThrows(OrderCreationException.class, () -> orderService.confirmOrder(MOCK_ID));
    }

    @Test
    void confirmOrderAlreadyConfirmedTest(){
        Order expectedOrder = TestOrderDataUtil.createOrder();
        expectedOrder.setStatus(Status.CONFIRMED);
        when(orderRepository.findById(MOCK_ID)).thenReturn(Optional.of(expectedOrder));
        assertThrows(OrderCreationException.class, () -> orderService.confirmOrder(MOCK_ID));
    }
}
