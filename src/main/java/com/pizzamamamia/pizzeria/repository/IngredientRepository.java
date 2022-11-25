package com.pizzamamamia.pizzeria.repository;

import com.pizzamamamia.pizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
