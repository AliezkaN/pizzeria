package com.pizzamamamia.pizzeria.service.impl;

import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.exception.*;
import com.pizzamamamia.pizzeria.model.*;
import com.pizzamamamia.pizzeria.repository.CustomerRepository;
import com.pizzamamamia.pizzeria.repository.IngredientRepository;
import com.pizzamamamia.pizzeria.repository.OrderRepository;
import com.pizzamamamia.pizzeria.repository.PizzaRepository;
import com.pizzamamamia.pizzeria.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.pizzamamamia.pizzeria.service.mappers.IngredientToIngredientDtoMapper.mapIngredientToIngredientDto;
import static com.pizzamamamia.pizzeria.service.mappers.IngredientToIngredientDtoMapper.mapListOfIngredientsToIngredientsDto;
import static com.pizzamamamia.pizzeria.service.mappers.PizzaToPizzaDtoMapper.mapPizzaToPizzaDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final CustomerRepository customerRepository;
    private final PizzaRepository pizzaRepository;
    private final OrderRepository orderRepository;
    private final IngredientRepository ingredientRepository;

    @Override
    public OrderDto createOrder(String email, Long pizzaId) {
        log.info("OrderService --> createOrder with customer with email{} and pizza with id", pizzaId);

        Customer customer = customerRepository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(PizzaNotFoundException::new);

        if (pizza.getPrice().equals(BigDecimal.ZERO)){
            throw new OrderCreationException("Pizza is empty please choose another one!");
        }

        Order order = Order.builder()
                .customer(customer)
                .pizza(pizza)
                .status(Status.CREATED)
                .creationDateTime(LocalDateTime.now())
                .build();

        Order storedOrder = orderRepository.save(order);
        return mapOrderToOrderDto(storedOrder);
    }

    @Override
    public List<OrderDto> getOrders(String email) {
        log.info("OrderService --> get all Orders with customer with email{}",email);
        Customer customer = customerRepository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        List<Order> orders = orderRepository.findAllByCustomer(customer);
        return mapListOfOrdersToOrdersDto(orders);
    }

    @Override
    public List<OrderDto> getCreatedOrders(String email) {
        log.info("OrderService --> get all created Orders by customer with email{}",email);
        Customer customer = customerRepository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        List<Order> orders = orderRepository.findAllByCustomerAndStatus(customer,Status.CREATED);
        return mapListOfOrdersToOrdersDto(orders);
    }

    @Override
    public OrderDto addTopping(Long orderId, Long ingredientId) {
        log.info("OrderService --> add topping to pizza by orderId ({}) and ingredientId ({})",orderId, ingredientId);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);

        Status status = order.getStatus();
        if (status.equals(Status.CARTED)){
            throw new OrderCreationException("Order already in the cart! Please delete from cart and add topping!");
        }

        if (status.equals(Status.CONFIRMED)){
            throw new OrderCreationException("Order already confirmed!");
        }

        if(Objects.isNull(order.getToppings())){
            order.setToppings(new ArrayList<>());
        }

        order.getToppings().add(ingredient);
        order.setModificationDateTime(LocalDateTime.now());
        Order storedOrder = orderRepository.save(order);
        return mapOrderToOrderDto(storedOrder);
    }

    @Override
    public OrderDto deleteTopping(Long orderId, Long ingredientId) {
        log.info("OrderService --> delete topping from pizza by orderId ({}) and ingredientId ({})",orderId, ingredientId);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);

        Status status = order.getStatus();
        if (status.equals(Status.CARTED)){
            throw new OrderCreationException("Order already in the cart! Please delete from cart and delete topping!");
        }

        if (status.equals(Status.CONFIRMED)){
            throw new OrderCreationException("Order already confirmed!");
        }

        if(Objects.nonNull(order.getToppings())){
            order.getToppings().remove(ingredient);
        }

        order.setModificationDateTime(LocalDateTime.now());
        Order storedOrder = orderRepository.save(order);
        return mapOrderToOrderDto(storedOrder);
    }

    @Override
    public OrderDto addOrderToCart(Long orderId) {
        log.info("OrderService --> add order to cart by orderId ({})",orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        Status status = order.getStatus();

        if (status.equals(Status.CARTED)){
            throw new OrderCreationException("Order already in the cart!");
        }

        if (status.equals(Status.CONFIRMED)){
            throw new OrderCreationException("Order already confirmed!");
        }

        order.setStatus(Status.CARTED);
        order.setModificationDateTime(LocalDateTime.now());
        Order storedOrder = orderRepository.save(order);
        return mapOrderToOrderDto(storedOrder);
    }

    @Override
    public OrderDto deleteOrderFromCart(Long orderId) {
        log.info("OrderService --> delete order from cart by orderId ({})",orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        Status status = order.getStatus();

        if (status.equals(Status.CREATED)){
            throw new OrderCreationException("Order isn't in the cart!");
        }

        if (status.equals(Status.CONFIRMED)){
            throw new OrderCreationException("Order already confirmed!");
        }

        order.setStatus(Status.CREATED);
        order.setModificationDateTime(LocalDateTime.now());
        Order storedOrder = orderRepository.save(order);
        return mapOrderToOrderDto(storedOrder);
    }

    @Override
    public OrderDto confirmOrder(Long orderId) {
        log.info("OrderService --> confirm order by orderId ({})",orderId);
        Order order = orderRepository.findById(orderId).orElseThrow(OrderNotFoundException::new);

        Status status = order.getStatus();

        if (status.equals(Status.CREATED)){
            throw new OrderCreationException("Order isn't in the cart! Please add order to the cart to confirm it! ");
        }

        if (status.equals(Status.CONFIRMED)){
            throw new OrderCreationException("Order already confirmed!");
        }

        order.setStatus(Status.CONFIRMED);
        order.setModificationDateTime(LocalDateTime.now());
        Order storedOrder = orderRepository.save(order);
        return mapOrderToOrderDto(storedOrder);
    }

    @Override
    public List<OrderDto> getCartedOrders(String email) {
        log.info("OrderService --> get all carted orders");
        Customer customer = customerRepository.findByEmail(email).orElseThrow(CustomerNotFoundException::new);
        List<Order> orders = orderRepository.findAllByCustomerAndStatus(customer,Status.CARTED);
        return mapListOfOrdersToOrdersDto(orders);
    }

    private OrderDto mapOrderToOrderDto(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .pizza(mapPizzaToPizzaDto(order.getPizza()))
                .status(order.getStatus())
                .price(order.getPrice())
                .toppings(mapListOfIngredientsToIngredientsDto(order.getToppings()))
                .creationDateTime(order.getCreationDateTime())
                .modificationDateTime(order.getModificationDateTime())
                .build();
    }

    private List<OrderDto> mapListOfOrdersToOrdersDto(List<Order> orders){
        return orders.stream()
                .map( order -> mapOrderToOrderDto(order))
                .collect(Collectors.toList());
    }
}
