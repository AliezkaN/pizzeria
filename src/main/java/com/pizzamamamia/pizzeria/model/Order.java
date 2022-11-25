package com.pizzamamamia.pizzeria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Pizza pizza;

    @ManyToMany
    private List<Ingredient> toppings;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Transient
    private BigDecimal price;

    private LocalDateTime creationDateTime;
    private LocalDateTime modificationDateTime;

    public BigDecimal getPrice() {

        final BigDecimal pizzaPrice = pizza.getPrice();

        if (Objects.isNull(toppings) || toppings.size() == 0){
            return pizzaPrice;
        }

        return toppings.stream()
                .map( ingredient -> ingredient.getPrice())
                .reduce(BigDecimal.ZERO,BigDecimal::add)
                .add(pizzaPrice);
    }
}
