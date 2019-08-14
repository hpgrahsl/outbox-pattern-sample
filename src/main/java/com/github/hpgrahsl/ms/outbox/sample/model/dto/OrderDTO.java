package com.github.hpgrahsl.ms.outbox.sample.model.dto;

import com.github.hpgrahsl.ms.outbox.sample.model.PurchaseOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDTO {

    private long id;
    private long customerId;
    private LocalDateTime orderDate;
    private List<OrderLineDTO> lineItems;

    public OrderDTO() {
    }

    public OrderDTO(long id, long customerId, LocalDateTime orderDate, List<OrderLineDTO> lineItems) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.lineItems = lineItems;
    }

    public static OrderDTO from(PurchaseOrder order) {
        return new OrderDTO(order.getId(), order.getCustomerId(), order.getOrderDate(),
                order.getLineItems().stream().map(OrderLineDTO::from).collect(Collectors.toList()));
    }

    public static PurchaseOrder to(OrderDTO order) {
        return new PurchaseOrder(order.getCustomerId(), order.getOrderDate(),
                order.getLineItems().stream().map(OrderLineDTO::to).collect(Collectors.toList()));
    }

    public long getId() {
        return id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderLineDTO> getLineItems() {
        return lineItems;
    }

}
