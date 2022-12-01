package com.pizzamamamia.pizzeria.testUtils;

import com.pizzamamamia.pizzeria.controller.dto.OrderDto;
import com.pizzamamamia.pizzeria.controller.dto.PizzaDto;
import com.pizzamamamia.pizzeria.model.Ingredient;
import com.pizzamamamia.pizzeria.model.Order;
import com.pizzamamamia.pizzeria.model.Pizza;
import com.pizzamamamia.pizzeria.model.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestOrderDataUtil {

    public static final Status MOCK_CREATED_STATUS = Status.CREATED;
    private static final Pizza MOCK_PIZZA = TestPizzaDataUtil.createPizza();
    private static final PizzaDto MOCK_PIZZA_DTO = TestPizzaDataUtil.createPizzaDto();
    private static final Ingredient MOCK_INGREDIENT = TestIngredientDataUtil.createIngredient();

    static {
        MOCK_PIZZA.getIngredients().add(MOCK_INGREDIENT);
    }

    public static Order createOrder(){
        return Order.builder()
                .customer(TestCustomerDataUtil.createCustomer())
                .pizza(MOCK_PIZZA)
                .toppings(new ArrayList<>())
                .status(MOCK_CREATED_STATUS)
                .build();
    }

    public static OrderDto createOrderDto(){
        return OrderDto.builder()
                .pizza(MOCK_PIZZA_DTO)
                .toppings(new ArrayList<>())
                .status(MOCK_CREATED_STATUS)
                .build();
    }

}
