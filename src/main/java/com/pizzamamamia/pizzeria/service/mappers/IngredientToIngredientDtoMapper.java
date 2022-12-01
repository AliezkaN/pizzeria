package com.pizzamamamia.pizzeria.service.mappers;

import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class IngredientToIngredientDtoMapper {

    public static IngredientDto mapIngredientToIngredientDto(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price(ingredient.getPrice())
                .build();
    }

    public static Ingredient mapIngredientDtoToIngredient(IngredientDto ingredientDto){
        return Ingredient.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getName())
                .price(ingredientDto.getPrice())
                .build();
    }

    public static List<IngredientDto> mapListOfIngredientsToIngredientsDto(List<Ingredient> ingredients){

        if(Objects.isNull(ingredients)){
            return new ArrayList<>();
        }

        return ingredients.stream()
                .map( ingredient -> mapIngredientToIngredientDto(ingredient))
                .collect(Collectors.toList());
    }

    public static List<Ingredient> mapListOfIngredientsDtoToIngredients(List<IngredientDto> ingredients){

        if(Objects.isNull(ingredients)){
            return new ArrayList<>();
        }

        return ingredients.stream()
                .map( ingredient -> mapIngredientDtoToIngredient(ingredient))
                .collect(Collectors.toList());
    }
}
