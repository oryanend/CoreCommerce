package com.oryanend.dscommerce.dto;

import com.oryanend.dscommerce.entities.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;

public class OrderItemDTO {

    @Schema(description = "ID do produto",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Long productId;

    @Schema(description = "Quantidade do produto",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer quantity;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String name;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double price;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private String imgUrl;

    public OrderItemDTO(Long productId, String name, Double price, Integer quantity, String imgUrl) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imgUrl = imgUrl;
    }

    public OrderItemDTO(OrderItem entity) {
        this.productId = entity.getProduct().getId();
        this.name = entity.getProduct().getName();
        this.price = entity.getPrice();
        this.quantity = entity.getQuantity();
        this.imgUrl = entity.getProduct().getImgUrl();
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getSubTotal(){
        return price * quantity;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
