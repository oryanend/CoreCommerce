package com.oryanend.corecommerce.services;

import com.oryanend.corecommerce.dto.CategoryDTO;
import com.oryanend.corecommerce.dto.ProductDTO;
import com.oryanend.corecommerce.dto.ProductMinDTO;
import com.oryanend.corecommerce.entities.Category;
import com.oryanend.corecommerce.entities.Product;
import com.oryanend.corecommerce.repositories.ProductRepository;
import com.oryanend.corecommerce.services.execeptions.DatabaseException;
import com.oryanend.corecommerce.services.execeptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;



    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product result = repository.findById(id).orElseThrow( () -> new ResourceNotFoundException("Recurso não encontrado!"));
        return new ProductDTO(result);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable){
        Page<Product> products = repository.searchByName(name,pageable);
        return products.map(ProductMinDTO::new);
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
        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()){
            Category cat = new Category();
            cat.setId(catDTO.getId());
            entity.getCategories().add(cat);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado!");
        }
        try {
            repository.deleteById(id);
        }
        catch(DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial.");
        }
        repository.deleteById(id);
    }

}
