package com.oryanend.dscomerce.repositories;

import com.oryanend.dscomerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
