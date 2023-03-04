package com.pizzamamamia.pizzeria.service.impl;

import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.exception.IngredientNotFoundException;
import com.pizzamamamia.pizzeria.exception.PizzaNotFoundException;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.model.Pizza;
import com.pizzamamamia.pizzeria.repository.IngredientRepository;
import com.pizzamamamia.pizzeria.repository.PizzaRepository;
import com.pizzamamamia.pizzeria.service.PizzaService;
import com.pizzamamamia.pizzeria.service.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PizzaServiceImpl implements PizzaService {

    private final PizzaRepository pizzaRepository;
    private final IngredientRepository ingredientRepository;
    private final Mapper<Pizza, PizzaDto> mapper;

    @Override
    public List<PizzaDto> getAll() {
        log.info("PizzaService --> get all pizzas");
        return pizzaRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PizzaDto getPizza(Long id) {
        log.info("PizzaService --> getPizza by id{}",id);
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        return mapper.toDto(pizza);
    }

    @Override
    public PizzaDto createPizza(PizzaDto pizzaDto) {
        log.info("PizzaService --> createPizza {}", pizzaDto);
        Pizza pizza = mapper.toDomain(pizzaDto);
        pizza = pizzaRepository.save(pizza);
        return mapper.toDto(pizza);
    }

    @Override
    public PizzaDto updatePizza(Long id, PizzaDto pizzaDto) {
        log.info("PizzaService --> updatePizza with id {}", id);
        Pizza persistentPizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);

        final String name = pizzaDto.getName();
        if (Objects.nonNull(name)){
            persistentPizza.setName(name);
        }

        Pizza storedPizza = pizzaRepository.save(persistentPizza);
        log.info("PizzaService --> Pizza with id {} successfully updated", storedPizza.getId());
        return mapper.toDto(storedPizza);
    }

    @Override
    public PizzaDto addIngredient(Long pizzaId, Long ingredientId) {
        log.info("PizzaService --> add ingredient with id {} to pizza with id{}", ingredientId,pizzaId);
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(PizzaNotFoundException::new);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);

        if (Objects.isNull(pizza.getIngredients())){
            pizza.setIngredients(new ArrayList<>());
        }

        pizza.getIngredients().add(ingredient);
        Pizza storedPizza = pizzaRepository.save(pizza);
        return mapper.toDto(storedPizza);
    }

    @Override
    public PizzaDto deleteIngredient(Long pizzaId, Long ingredientId) {
        log.info("PizzaService --> delete ingredient with id {} from pizza with id{}", ingredientId,pizzaId);
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(PizzaNotFoundException::new);
        Ingredient ingredient = ingredientRepository.findById(ingredientId).orElseThrow(IngredientNotFoundException::new);

        if (Objects.nonNull(pizza.getIngredients()) && pizza.getIngredients().size() != 0){
            pizza.getIngredients().remove(ingredient);
        }

        Pizza storedPizza = pizzaRepository.save(pizza);
        return mapper.toDto(storedPizza);
    }

    @Override
    public void deletePizza(Long id) {
        log.info("PizzaService --> deletePizza with id {}", id);
        Pizza pizza = pizzaRepository.findById(id).orElseThrow(PizzaNotFoundException::new);
        pizzaRepository.delete(pizza);
    }
}
