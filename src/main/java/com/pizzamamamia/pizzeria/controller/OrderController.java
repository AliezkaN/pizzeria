package com.pizzamamamia.pizzeria.controller;

import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PatchMapping(value = "/{orderId}/addTopping/{ingredientId}")
    public OrderDto addToppingToPizza(@PathVariable Long orderId, @PathVariable Long ingredientId){
        log.info("add topping to pizza by orderId ({}) and ingredientId ({})", orderId, ingredientId);
        return orderService.addTopping(orderId, ingredientId);
    }

    @PatchMapping(value = "/{orderId}/deleteTopping/{ingredientId}")
    public OrderDto deleteToppingFromPizza(@PathVariable Long orderId, @PathVariable Long ingredientId){
        log.info("delete topping from pizza by orderId ({}) and ingredientId ({})", orderId, ingredientId);
        return orderService.deleteTopping(orderId, ingredientId);
    }

    @PatchMapping(value = "/{orderId}/addOrderToCart")
    public OrderDto addOrderToCart(@PathVariable Long orderId){
        return orderService.addOrderToCart(orderId);
    }

    @PatchMapping(value = "/{orderId}/deleteOrderFromCart")
    public OrderDto deleteOrderFromCart(@PathVariable Long orderId){
        return orderService.deleteOrderFromCart(orderId);
    }

    @PatchMapping(value = "/{orderId}/confirmOrder")
    public OrderDto confirmOrder(@PathVariable Long orderId){
        return orderService.confirmOrder(orderId);
    }
}
