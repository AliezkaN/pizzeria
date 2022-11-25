package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;

import java.util.List;

public interface PizzaService {
    List<PizzaDto> getAll();
    PizzaDto getPizza(Long id);
    PizzaDto createPizza(PizzaDto pizzaDto);
    PizzaDto updatePizza(Long id, PizzaDto pizzaDto);
    PizzaDto addIngredient(Long pizzaId, Long ingredientId);
    PizzaDto deleteIngredient(Long pizzaId, Long ingredientId);
    void deletePizza(Long id);
}
