package com.oryanend.dscomerce.services;

import com.oryanend.dscomerce.dto.ProductDTO;
import com.oryanend.dscomerce.entities.Product;
import com.oryanend.dscomerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;



    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> result = repository.findById(id);
        Product product = result.get();

        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> products = repository.findAll(pageable);
        return products.map(ProductDTO::new);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id){
        Product entity = repository.getReferenceById(id);
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);

        return new ProductDTO(entity);
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }


}
