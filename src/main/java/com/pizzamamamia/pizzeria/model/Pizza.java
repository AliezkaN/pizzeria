package com.pizzamamamia.pizzeria.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pizza")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Transient
    private BigDecimal price;
    @ManyToMany
    private List<Ingredient> ingredients;

    public BigDecimal getPrice() {

        if(Objects.isNull(ingredients) || ingredients.size() == 0){
            return BigDecimal.ZERO;
        }

        return ingredients.stream()
                .map( ingredient -> ingredient.getPrice())
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}

