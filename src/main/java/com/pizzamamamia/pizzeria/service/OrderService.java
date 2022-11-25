package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(String email, Long pizzaId);
    OrderDto addTopping(Long orderId, Long ingredientId);
    OrderDto deleteTopping(Long orderId, Long ingredientId);
    OrderDto addOrderToCart(Long orderId);
    OrderDto confirmOrder(Long orderId);
    OrderDto deleteOrderFromCart(Long orderId);
    List<OrderDto> getOrders(String email);
    List<OrderDto> getCartedOrders(String email);
    List<OrderDto> getCreatedOrders(String email);
}
