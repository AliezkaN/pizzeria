package com.pizzamamamia.pizzeria.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientDto {
    @JsonProperty(access = READ_ONLY)
    private Long id;

    @NotBlank(message = "'name' shouldn't be empty")
    private String name;

    @Positive
    private BigDecimal price;
}
