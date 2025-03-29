package com.oryanend.dscommerce.services;

import com.oryanend.dscommerce.dto.OrderDTO;
import com.oryanend.dscommerce.dto.OrderItemDTO;
import com.oryanend.dscommerce.dto.ProductDTO;
import com.oryanend.dscommerce.entities.*;
import com.oryanend.dscommerce.repositories.OrderItemRepository;
import com.oryanend.dscommerce.repositories.OrderRepository;
import com.oryanend.dscommerce.repositories.ProductRepository;
import com.oryanend.dscommerce.services.execeptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order result = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado!"));
        return new OrderDTO(result);
    }


    @Transactional
    public OrderDTO insert(OrderDTO orderDTO) {
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDTO : orderDTO.getItems()){
            Product product = productRepository.getReferenceById(itemDTO.getProductId());
            OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
