package com.pizzamamamia.pizzeria.model;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private Long id;
    private Customer customer;
    private Pizza pizza;
    private List<Ingredient> toppings;
    private BigDecimal price;
}
