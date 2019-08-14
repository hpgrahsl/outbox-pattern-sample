package com.github.hpgrahsl.ms.outbox.sample.model.dto;

import com.github.hpgrahsl.ms.outbox.sample.model.OrderLineStatus;

public class OrderLineStatusUpdateDTO {

    private OrderLineStatus newStatus;

    public OrderLineStatusUpdateDTO() {
    }

    public OrderLineStatusUpdateDTO(OrderLineStatus newStatus) {
        this.newStatus = newStatus;
    }

    public OrderLineStatus getNewStatus() {
        return newStatus;
    }

}
