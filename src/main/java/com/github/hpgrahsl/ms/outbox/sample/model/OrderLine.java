package com.github.hpgrahsl.ms.outbox.sample.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_line_ids")
    @SequenceGenerator(name = "order_line_ids", sequenceName = "seq_order_line", allocationSize = 10)
    private Long id;

    private String item;

    private int quantity;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private PurchaseOrder purchaseOrder;

    @Enumerated(EnumType.STRING)
    private OrderLineStatus status;

    OrderLine() {
    }

    public OrderLine(String item, int quantity, BigDecimal totalPrice) {
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = OrderLineStatus.ENTERED;
    }

    public OrderLine(String item, int quantity, BigDecimal totalPrice, OrderLineStatus orderLineStatus) {
        this.item = item;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.status = orderLineStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderLineStatus getStatus() {
        return status;
    }

    public void setStatus(OrderLineStatus status) {
        this.status = status;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", item='" + item + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
