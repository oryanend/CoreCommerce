package com.oryanend.dscommerce.dto;

import com.oryanend.dscommerce.entities.Order;
import com.oryanend.dscommerce.entities.OrderItem;
import com.oryanend.dscommerce.entities.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "DTO para pedido")
public class OrderDTO {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant moment;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private OrderStatus status;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private ClientDTO client;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private PaymentDTO payment;

    @NotEmpty(message = "Deve ter pelo menos um item")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private List<OrderItemDTO> items = new ArrayList<>();

    public OrderDTO(Long id, Instant moment, OrderStatus status, ClientDTO client, PaymentDTO payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public OrderDTO(Order entity) {
        this.id = entity.getId();
        this.moment = entity.getMoment();
        this.status = entity.getStatus();
        this.client = new ClientDTO(entity.getClient());
        this.payment = (entity.getPayment() == null) ? null : new PaymentDTO(entity.getPayment());

        for (OrderItem item : entity.getItems()){
            items.add(new OrderItemDTO(item));
        }
    }

    public Long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public ClientDTO getClient() {
        return client;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public Double getTotal() {
        double sum = 0.0;
        for (OrderItemDTO item : items) {
            sum += item.getSubTotal();
        }
        return sum;
    }
}
