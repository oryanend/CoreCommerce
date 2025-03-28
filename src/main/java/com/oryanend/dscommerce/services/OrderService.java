package com.oryanend.dscommerce.services;

import com.oryanend.dscommerce.dto.OrderDTO;
import com.oryanend.dscommerce.dto.ProductDTO;
import com.oryanend.dscommerce.entities.Order;
import com.oryanend.dscommerce.entities.Product;
import com.oryanend.dscommerce.repositories.OrderRepository;
import com.oryanend.dscommerce.repositories.ProductRepository;
import com.oryanend.dscommerce.services.execeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id){
        Order result = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado!"));
        return new OrderDTO(result);
    }
}
