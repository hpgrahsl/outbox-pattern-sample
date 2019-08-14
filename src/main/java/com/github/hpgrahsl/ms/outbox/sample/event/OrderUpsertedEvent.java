package com.github.hpgrahsl.ms.outbox.sample.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.hpgrahsl.ms.outbox.sample.model.PurchaseOrder;
import com.github.hpgrahsl.ms.outbox.sample.outbox.Outboxable;

import java.time.Instant;

public class OrderUpsertedEvent implements Outboxable {

    private static ObjectMapper MAPPER = new ObjectMapper();

    private final Long id;
    private final JsonNode payload;
    private final Long timestamp;

    static {
        MAPPER.registerModule(new JavaTimeModule());
    }

    private OrderUpsertedEvent(Long id, JsonNode payload) {
        this.id = id;
        this.payload = payload;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public static OrderUpsertedEvent of(PurchaseOrder order) {
        return new OrderUpsertedEvent(order.getId(), MAPPER.valueToTree(order));
    }

    @Override
    public String getAggregateId() {
        return String.valueOf(id);
    }

    @Override
    public String getAggregateType() {
        return PurchaseOrder.class.getName();
    }

    @Override
    public String getType() {
        return this.getClass().getName();
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

}
