package com.pizzamamamia.pizzeria.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pizzamamamia.pizzeria.controller.validation.group.OnCreate;
import com.pizzamamamia.pizzeria.controller.validation.group.OnUpdate;
import com.pizzamamamia.pizzeria.model.Ingredient;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PizzaDto {
    @JsonProperty(access = READ_ONLY)
    private Long id;
    @NotBlank(message = "'name' shouldn't be empty")
    private String name;

    @Null(message = "'price' should be absent in request",groups = OnCreate.class)
    private BigDecimal price;
    @Null(message = "'ingredients' should be absent in request",groups = OnCreate.class)
    private List<Ingredient> ingredients;
}
