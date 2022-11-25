package com.pizzamamamia.pizzeria.service;

import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;

import java.util.List;

public interface IngredientService {
    List<IngredientDto> getAll();
    IngredientDto getIngredient(Long id);
    IngredientDto createIngredient(IngredientDto ingredientDto);
    IngredientDto updateIngredient(Long id, IngredientDto ingredientDto);
    void deleteIngredient(Long id);
}
