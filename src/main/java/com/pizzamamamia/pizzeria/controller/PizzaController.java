package com.pizzamamamia.pizzeria.controller;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.controller.validation.group.OnCreate;
import com.pizzamamamia.pizzeria.service.PizzaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

}
