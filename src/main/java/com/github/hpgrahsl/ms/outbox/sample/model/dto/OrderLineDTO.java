package com.github.hpgrahsl.ms.outbox.sample.model.dto;

import com.github.hpgrahsl.ms.outbox.sample.model.OrderLine;
import com.github.hpgrahsl.ms.outbox.sample.model.OrderLineStatus;

import java.math.BigDecimal;

public class OrderLineDTO {

    private Long id;
    private String item;
    private int quantity;
    private BigDecimal totalPrice;
    private OrderLineStatus orderLineStatus;

    public OrderLineDTO() {
    }

    public OrderLineDTO(Long id, String item, int quantity, BigDecimal totalPrice, OrderLineStatus orderLineStatus) {
        this.id = id;
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.orderLineStatus = orderLineStatus;

    }

    public static OrderLineDTO from(OrderLine line) {
        return new OrderLineDTO(line.getId(), line.getItem(), line.getQuantity(), line.getTotalPrice(), line.getStatus());
    }

    public static OrderLine to(OrderLineDTO line) {
        return new OrderLine(line.getItem(), line.getQuantity(), line.getTotalPrice());
    }

    public Long getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public OrderLineStatus getOrderLineStatus() {
        return orderLineStatus;
    }

    public void setOrderLineStatus(OrderLineStatus orderLineStatus) {
        this.orderLineStatus = orderLineStatus;
    }
}
