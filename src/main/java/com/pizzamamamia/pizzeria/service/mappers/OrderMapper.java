package com.pizzamamamia.pizzeria.service.mappers;

import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.model.Order;
import com.pizzamamamia.pizzeria.model.Pizza;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper implements Mapper<Order, OrderDto> {

    private final Mapper<Pizza, PizzaDto> pizzaMapper;
    private final Mapper<Ingredient, IngredientDto> ingredientMapper;

    @Override
    public OrderDto toDto(Order domain) {
        return new OrderDto()
                .setId(domain.getId())
                .setPizza(pizzaMapper.toDto(domain.getPizza()))
                .setStatus(domain.getStatus())
                .setPrice(domain.getPrice())
                .setCreationDateTime(domain.getCreationDateTime())
                .setModificationDateTime(domain.getModificationDateTime())
                .setToppings(domain.getToppings()
                        .stream()
                        .map(ingredientMapper::toDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public Order toDomain(OrderDto dto) {
        return new Order()
                .setId(dto.getId())
                .setPizza(pizzaMapper.toDomain(dto.getPizza()))
                .setStatus(dto.getStatus())
                .setPrice(dto.getPrice())
                .setCreationDateTime(dto.getCreationDateTime())
                .setModificationDateTime(dto.getModificationDateTime())
                .setToppings(dto.getToppings()
                        .stream()
                        .map(ingredientMapper::toDomain)
                        .collect(Collectors.toList()));
    }
}
