package com.oryanend.corecommerce.repositories;

import com.oryanend.corecommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
