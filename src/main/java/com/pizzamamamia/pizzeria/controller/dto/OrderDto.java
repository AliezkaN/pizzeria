package com.pizzamamamia.pizzeria.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.model.Pizza;
import com.pizzamamamia.pizzeria.model.Status;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    @JsonProperty(access = READ_ONLY)
    private Long id;
    private PizzaDto pizza;
    private List<IngredientDto> toppings;
    private Status status;
    private BigDecimal price;
    private LocalDateTime creationDateTime;
    private LocalDateTime modificationDateTime;
}
