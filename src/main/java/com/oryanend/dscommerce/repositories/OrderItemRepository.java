package com.oryanend.dscommerce.repositories;

import com.oryanend.dscommerce.entities.Order;
import com.oryanend.dscommerce.entities.OrderItem;
import com.oryanend.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
