package com.oryanend.corecommerce.repositories;

import com.oryanend.corecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
