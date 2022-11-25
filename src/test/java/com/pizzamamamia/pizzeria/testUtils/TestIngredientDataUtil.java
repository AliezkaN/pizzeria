package com.pizzamamamia.pizzeria.testUtils;

import com.pizzamamamia.pizzeria.controller.dto.CustomerDto;
import com.pizzamamamia.pizzeria.controller.dto.IngredientDto;
import com.pizzamamamia.pizzeria.model.Customer;
import com.pizzamamamia.pizzeria.model.Ingredient;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestIngredientDataUtil {

    public static final Long MOCK_ID = 1L;
    public static final String MOCK_NAME = "Bechamel sauce";
    public static final BigDecimal MOCK_PRICE = BigDecimal.valueOf(35);

    public static final String MOCK_UPDATE_NAME = "Chicken";
    public static final BigDecimal MOCK_UPDATE_PRICE = BigDecimal.valueOf(45);

    public static Ingredient createIngredient() {
        return Ingredient.builder()
                .name(MOCK_NAME)
                .price(MOCK_PRICE)
                .build();
    }

    public static IngredientDto createIngredientDto() {
        return IngredientDto.builder()
                .name(MOCK_NAME)
                .price(MOCK_PRICE)
                .build();
    }
}
