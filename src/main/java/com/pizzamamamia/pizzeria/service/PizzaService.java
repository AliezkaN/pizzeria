package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;

import java.util.List;

public interface PizzaService {
    List<PizzaDto> getAll();
    PizzaDto getPizza(Long id);
    PizzaDto createPizza(PizzaDto pizzaDto);
}
