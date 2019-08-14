package com.github.hpgrahsl.ms.outbox.sample.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.hpgrahsl.ms.outbox.sample.event.OrderUpsertedEvent;
import com.github.hpgrahsl.ms.outbox.sample.outbox.Outboxable;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Entity
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "purchase_order_ids")
    @SequenceGenerator(name = "purchase_order_ids", sequenceName = "seq_purchase_order", allocationSize = 10)
    private Long id;

    private long customerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS", timezone = "UTC")
    private LocalDateTime orderDate;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "purchaseOrder",
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private List<OrderLine> lineItems;

    PurchaseOrder() {
    }

    public PurchaseOrder(long customerId, LocalDateTime orderDate, List<OrderLine> lineItems) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.lineItems = new ArrayList<>(lineItems);
        for (OrderLine orderLine : lineItems) {
            orderLine.setPurchaseOrder(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderLine> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<OrderLine> lineItems) {
        this.lineItems = lineItems;
    }

    public OrderLineStatus updateOrderLine(long orderLineId, OrderLineStatus newStatus) {
        for (OrderLine orderLine : lineItems) {
            if (orderLine.getId() == orderLineId) {
                OrderLineStatus oldStatus = orderLine.getStatus();
                orderLine.setStatus(newStatus);
                return oldStatus;
            }
        }

        throw new EntityNotFoundException("Order doesn't contain line with id " + orderLineId);
    }

    public BigDecimal getTotalValue() {
        return lineItems.stream()
                .map(OrderLine::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", lineItems=" + lineItems +
                "} ";
    }

    @DomainEvents
    private Collection<Outboxable> triggerOutboxEvents() {
        return Arrays.asList(OrderUpsertedEvent.of(this));
    }

}
