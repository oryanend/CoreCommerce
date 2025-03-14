package com.oryanend.dscomerce.controllers;

import com.oryanend.dscomerce.dto.ProductDTO;
import com.oryanend.dscomerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id){
        ProductDTO dto = productService.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(Pageable pageable){
        Page<ProductDTO> dto = productService.findAll(pageable);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO productDTO){
        productDTO = productService.insert(productDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(productDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO, @PathVariable Long id){
        productDTO = productService.update(productDTO, id);
        return ResponseEntity.ok().body(productDTO);
    }

}
