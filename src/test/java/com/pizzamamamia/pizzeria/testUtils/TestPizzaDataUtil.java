package com.pizzamamamia.pizzeria.testUtils;

import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.model.Pizza;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestPizzaDataUtil {

    public static final String MOCK_NAME = "Italiana";
    public static final String MOCK_UPDATE_NAME ="Hawaiian";

    public static Pizza createPizza(){
        return Pizza.builder()
                .name(MOCK_NAME)
                .ingredients(new ArrayList<>())
                .build();
    }

    public static PizzaDto createPizzaDto(){
        return PizzaDto.builder()
                .name(MOCK_NAME)
                .ingredients(new ArrayList<>())
                .build();
    }
}
