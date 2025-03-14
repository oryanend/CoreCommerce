package com.oryanend.dscomerce.controllers;

import com.oryanend.dscomerce.dto.ProductDTO;
import com.oryanend.dscomerce.entities.Product;
import com.oryanend.dscomerce.repositories.ProductRepository;
import com.oryanend.dscomerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ProductDTO teste(@PathVariable Long id){
        return productService.findById(id);
    }

}
