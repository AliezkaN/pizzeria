package com.pizzamamamia.pizzeria.controller;

import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<IngredientDto> getAll(){
        log.info("get all ingredients");
        return ingredientService.getAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public IngredientDto getIngredient(@PathVariable Long id){
        log.info("getIngredient by id {}", id);
        return ingredientService.getIngredient(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public IngredientDto createIngredient(@RequestBody @Validated IngredientDto ingredientDto){
        log.info("createIngredient with name {}", ingredientDto.getName());
        return ingredientService.createIngredient(ingredientDto);
    }

    @PatchMapping(value = "/{id}")
    public IngredientDto updateIngredient(@PathVariable Long id,
                                  @RequestBody @Validated IngredientDto ingredientDto){
        log.info("updateIngredient with id {}", id);
        return ingredientService.updateIngredient(id, ingredientDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Long id){
        log.info("deleteIngredient with id {}", id);
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
