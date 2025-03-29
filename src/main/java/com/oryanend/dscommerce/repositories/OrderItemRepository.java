package com.oryanend.dscommerce.repositories;

import com.oryanend.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
