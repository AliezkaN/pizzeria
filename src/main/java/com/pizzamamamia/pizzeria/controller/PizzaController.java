package com.pizzamamamia.pizzeria.controller;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.controller.validation.group.OnCreate;
import com.pizzamamamia.pizzeria.service.PizzaService;
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
@RequestMapping("/api/v1/pizza")
public class PizzaController {

    private final PizzaService pizzaService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<PizzaDto> getAll(){
        log.info("get all customers");
        return pizzaService.getAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PizzaDto getPizza(@PathVariable Long id){
        log.info("getPizza by id {}", id);
        return pizzaService.getPizza(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public PizzaDto createPizza(@RequestBody @Validated(OnCreate.class) PizzaDto pizzaDto){
        log.info("createPizza with id {}", pizzaDto.getId());
        return pizzaService.createPizza(pizzaDto);
    }

    @PatchMapping(value = "/{id}")
    public PizzaDto updatePizza(@PathVariable Long id,
                                          @RequestBody @Validated PizzaDto pizzaDto){
        log.info("updatePizza with id {}", id);
        return pizzaService.updatePizza(id, pizzaDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePizza(@PathVariable Long id){
        log.info("deletePizza with id {}", id);
        pizzaService.deletePizza(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{pizzaId}/addIngredient/{ingredientId}")
    public PizzaDto addIngredientToPizza(@PathVariable Long pizzaId, @PathVariable Long ingredientId){
        log.info("add ingredient to pizza by pizzaId ({}) and ingredientId ({})", pizzaId, ingredientId);
        return pizzaService.addIngredient(pizzaId, ingredientId);
    }

    @PatchMapping(value = "/{pizzaId}/deleteIngredient/{ingredientId}")
    public PizzaDto deleteIngredientFromPizza(@PathVariable Long pizzaId, @PathVariable Long ingredientId){
        log.info("delete ingredient from pizza by pizzaId ({}) and ingredientId ({})", pizzaId, ingredientId);
        return pizzaService.deleteIngredient(pizzaId, ingredientId);
    }

}
