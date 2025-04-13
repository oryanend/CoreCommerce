package com.oryanend.dscommerce.dto;

import com.oryanend.dscommerce.entities.Category;
import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDTO {

    @Schema(description = "ID da categoria",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Nome da categoria (opcional para atualização)",
            example = "Eletrônicos",
            accessMode = Schema.AccessMode.READ_ONLY)
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO(Category entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}