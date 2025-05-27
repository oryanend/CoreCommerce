package com.oryanend.corecommerce.repositories;

import com.oryanend.corecommerce.entities.OrderItem;
import com.oryanend.corecommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {

}
