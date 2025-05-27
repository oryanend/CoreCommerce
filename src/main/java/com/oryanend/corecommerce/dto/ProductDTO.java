package com.oryanend.corecommerce.dto;

import com.oryanend.corecommerce.entities.Category;
import com.oryanend.corecommerce.entities.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    @Schema(description = "ID do produto (gerado automaticamente)",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Size(min = 3, max = 80, message = "Nome precisa ter 3 de 80 caracteres")
    @NotBlank(message = "Campo requerido")
    @Schema(description = "Nome do produto",
            example = "Smartphone Premium",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Size(min = 10, message = "Descrição precisa ter no mínimo 10 caracteres")
    @NotBlank(message = "Campo requerido")
    @Schema(description = "Descrição detalhada do produto",
            example = "Smartphone com câmera de 108MP, 256GB de armazenamento...",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @NotNull(message = "Campo requerido")
    @Positive(message = "O preço deve ser positivo")
    @Schema(description = "Preço do produto",
            example = "2499.99",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;

    @Schema(description = "URL da imagem do produto",
            example = "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg")
    private String imgUrl;

    @NotEmpty(message = "Deve ter pelo menos uma categoria")
    @Schema(description = "Lista de categorias do produto",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CategoryDTO> categories = new ArrayList<>();

    // Construtores, getters e setters permanecem iguais
    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product product) {
        id = product.getId();
        name = product.getName();
        description = product.getDescription();
        price = product.getPrice();
        imgUrl = product.getImgUrl();
        for (Category cat : product.getCategories()){
            categories.add(new CategoryDTO(cat));
        }
    }

    // Getters e setters permanecem iguais
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}