package com.pizzamamamia.pizzeria.service.mappers;

import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.model.Pizza;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PizzaMapper implements Mapper<Pizza, PizzaDto> {

    private final Mapper<Ingredient, IngredientDto> ingredientMapper;

    @Override
    public PizzaDto toDto(Pizza domain) {
        return new PizzaDto()
                .setId(domain.getId())
                .setName(domain.getName())
                .setPrice(domain.getPrice())
                .setIngredients(domain.getIngredients()
                        .stream()
                        .map(ingredientMapper::toDto)
                        .collect(Collectors.toList()));
    }

    @Override
    public Pizza toDomain(PizzaDto dto) {
        return new Pizza()
                .setName(dto.getName());
    }
}
