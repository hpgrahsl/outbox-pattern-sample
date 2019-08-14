package com.github.hpgrahsl.ms.outbox.sample.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hpgrahsl.ms.outbox.sample.model.PurchaseOrder;
import com.github.hpgrahsl.ms.outbox.sample.outbox.Outboxable;
import com.github.hpgrahsl.ms.outbox.sample.model.OrderLineStatus;

import java.time.Instant;

public class OrderLineUpdatedEvent implements Outboxable {

    private static ObjectMapper MAPPER = new ObjectMapper();

    private final long orderId;
    private final JsonNode payload;
    private final long timestamp;


    public OrderLineUpdatedEvent(long orderId, JsonNode payload) {
        this.orderId = orderId;
        this.payload = payload;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static OrderLineUpdatedEvent of(long orderId, long orderLineId, OrderLineStatus newStatus,
                                           OrderLineStatus oldStatus) {

        JsonNode orderLineUpdate = MAPPER.createObjectNode()
                .put("orderId", orderId)
                .put("orderLineId", orderLineId)
                .put("oldStatus", oldStatus.name())
                .put("newStatus", newStatus.name());

        return new OrderLineUpdatedEvent(orderId, orderLineUpdate);
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(orderId);
    }

    @Override
    public String getAggregateType() {
        return PurchaseOrder.class.getName();
    }

    @Override
    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getPayload() {
        try {
            return MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getType() {
        return this.getClass().getName();
    }
}
