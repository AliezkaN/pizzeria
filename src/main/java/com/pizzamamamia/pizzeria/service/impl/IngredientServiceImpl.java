package com.pizzamamamia.pizzeria.service.impl;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.exception.IngredientNotFoundException;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.repository.IngredientRepository;
import com.pizzamamamia.pizzeria.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Override
    public List<IngredientDto> getAll() {
        log.info("IngredientService --> get all ingredients");
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return mapListOfIngredientsToIngredientsDto(ingredients);
    }

    @Override
    public IngredientDto getIngredient(Long id) {
        log.info("IngredientService --> getIngredient by id{}",id);
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(IngredientNotFoundException::new);
        return mapIngredientToIngredientDto(ingredient);
    }

    @Override
    public IngredientDto createIngredient(IngredientDto ingredientDto) {
        log.info("IngredientService --> createIngredient {}", ingredientDto);
        Ingredient ingredient = mapIngredientDtoToIngredient(ingredientDto);
        ingredient = ingredientRepository.save(ingredient);
        return mapIngredientToIngredientDto(ingredient);
    }

    @Override
    public IngredientDto updateIngredient(Long id, IngredientDto ingredientDto) {
        log.info("IngredientService --> updateIngredient with id {}", id);
        Ingredient persistedIngredient = ingredientRepository.findById(id).orElseThrow(IngredientNotFoundException::new);

        final String name = ingredientDto.getName();
        if (Objects.nonNull(name)){
            persistedIngredient.setName(name);
        }

        final BigDecimal price = ingredientDto.getPrice();
        if (Objects.nonNull(price)){
            persistedIngredient.setPrice(price);
        }

        Ingredient storedIngredient = ingredientRepository.save(persistedIngredient);
        log.info("IngredientService --> Ingredient with id {} successfully updated", storedIngredient.getId());
        return mapIngredientToIngredientDto(storedIngredient);
    }

    @Override
    public void deleteIngredient(Long id) {
        log.info("IngredientService --> deleteIngredient with id {}", id);
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(IngredientNotFoundException::new);
        ingredientRepository.delete(ingredient);
    }

    private IngredientDto mapIngredientToIngredientDto(Ingredient ingredient){
        return IngredientDto.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .price(ingredient.getPrice())
                .build();
    }

    private Ingredient mapIngredientDtoToIngredient(IngredientDto ingredientDto){
        return Ingredient.builder()
                .id(ingredientDto.getId())
                .name(ingredientDto.getName())
                .price(ingredientDto.getPrice())
                .build();
    }

    private List<IngredientDto> mapListOfIngredientsToIngredientsDto(List<Ingredient> ingredients){
        return ingredients.stream()
                .map( ingredient -> mapIngredientToIngredientDto(ingredient))
                .collect(Collectors.toList());
    }
}
