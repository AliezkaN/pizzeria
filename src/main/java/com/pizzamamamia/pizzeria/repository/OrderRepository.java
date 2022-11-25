package com.pizzamamamia.pizzeria.repository;

import com.pizzamamamia.pizzeria.model.Customer;
import com.pizzamamamia.pizzeria.model.Order;
import com.pizzamamamia.pizzeria.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomer(Customer customer);
    List<Order> findAllByCustomerAndStatus(Customer customer, Status status);
}
