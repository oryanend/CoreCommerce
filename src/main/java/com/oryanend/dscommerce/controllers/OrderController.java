package com.oryanend.dscommerce.controllers;

import com.oryanend.dscommerce.dto.OrderDTO;
import com.oryanend.dscommerce.dto.ProductDTO;
import com.oryanend.dscommerce.dto.ProductMinDTO;
import com.oryanend.dscommerce.services.OrderService;
import com.oryanend.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id){
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO orderDTO){
        orderDTO = service.insert(orderDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(orderDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(orderDTO);
    }
}
