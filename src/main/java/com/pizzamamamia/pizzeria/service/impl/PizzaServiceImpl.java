package com.pizzamamamia.pizzeria.service.impl;

import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.exception.PizzaNotFoundException;
import com.pizzamamamia.pizzeria.model.Pizza;
import com.pizzamamamia.pizzeria.repository.PizzaRepository;
import com.pizzamamamia.pizzeria.service.PizzaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;

    @Override
    public List<PizzaDto> getAll() {
        List<Pizza> pizzas = pizzaRepository.findAll();
        System.out.println(pizzas);
        return mapListOfPizzaToPizzaDto(pizzas);
    }

    @Override
    public PizzaDto getPizza(Long id) {
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        return mapPizzaToPizzaDto(pizza);
    }

    @Override
    public PizzaDto createPizza(PizzaDto pizzaDto) {
        Pizza pizza = mapPizzaDtoToPizza(pizzaDto);
        pizza = pizzaRepository.save(pizza);
        return mapPizzaToPizzaDto(pizza);
    }

    private List<PizzaDto> mapListOfPizzaToPizzaDto(List<Pizza> pizzas){
        return pizzas.stream()
                .map(pizza -> mapPizzaToPizzaDto(pizza))
                .collect(Collectors.toList());
    }

    private PizzaDto mapPizzaToPizzaDto(Pizza pizza) {
        return PizzaDto.builder()
                .id(pizza.getId())
                .name(pizza.getName())
                .price(pizza.getPrice())
                .ingredients(pizza.getIngredients())
                .build();
    }

    private Pizza mapPizzaDtoToPizza(PizzaDto pizzaDto){
        return Pizza.builder()
                .id(pizzaDto.getId())
                .name(pizzaDto.getName())
                .price(pizzaDto.getPrice())
                .ingredients(pizzaDto.getIngredients())
                .build();
    }
}
