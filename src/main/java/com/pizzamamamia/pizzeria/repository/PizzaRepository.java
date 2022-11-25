package com.pizzamamamia.pizzeria.repository;

import com.pizzamamamia.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza,Long> {
}
