package com.pizzamamamia.pizzeria.service.mappers;

import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.model.Ingredient;
import org.springframework.stereotype.Component;

@Component
public class IngredientMapper implements Mapper<Ingredient, IngredientDto> {
    @Override
    public IngredientDto toDto(Ingredient domain) {
        return new IngredientDto()
                .setId(domain.getId())
                .setName(domain.getName())
                .setPrice(domain.getPrice());
    }

    @Override
    public Ingredient toDomain(IngredientDto dto) {
        return new Ingredient()
                .setId(dto.getId())
                .setName(dto.getName())
                .setPrice(dto.getPrice());
    }
}
