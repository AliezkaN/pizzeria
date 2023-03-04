package com.pizzamamamia.pizzeria.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Pizza pizza;
    @ManyToMany
    private List<Ingredient> toppings = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private Status status;
    @Transient
    private BigDecimal price;
    private LocalDateTime creationDateTime;
    private LocalDateTime modificationDateTime;

    public BigDecimal getPrice() {
        final BigDecimal pizzaPrice = pizza.getPrice();
        return Objects.isNull(toppings) || toppings.size() == 0
                ? pizzaPrice
                : toppings.stream()
                .map(Ingredient::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(pizzaPrice);
    }
}
