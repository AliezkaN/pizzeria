package com.pizzamamamia.pizzeria.service.mappers;

import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.model.Pizza;

import java.util.List;
import java.util.stream.Collectors;

import static com.pizzamamamia.pizzeria.service.mappers.IngredientToIngredientDtoMapper.mapListOfIngredientsDtoToIngredients;
import static com.pizzamamamia.pizzeria.service.mappers.IngredientToIngredientDtoMapper.mapListOfIngredientsToIngredientsDto;

public final class PizzaToPizzaDtoMapper {

    public static List<PizzaDto> mapListOfPizzaToPizzaDto(List<Pizza> pizzas){
        return pizzas.stream()
                .map(pizza -> mapPizzaToPizzaDto(pizza))
                .collect(Collectors.toList());
    }

    public static PizzaDto mapPizzaToPizzaDto(Pizza pizza) {
        return PizzaDto.builder()
                .id(pizza.getId())
                .name(pizza.getName())
                .price(pizza.getPrice())
                .ingredients(mapListOfIngredientsToIngredientsDto(pizza.getIngredients()))
                .build();
    }

    public static Pizza mapPizzaDtoToPizza(PizzaDto pizzaDto){
        return Pizza.builder()
                .id(pizzaDto.getId())
                .name(pizzaDto.getName())
                .price(pizzaDto.getPrice())
                .ingredients(mapListOfIngredientsDtoToIngredients(pizzaDto.getIngredients()))
                .build();
    }
}
