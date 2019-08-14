package com.github.hpgrahsl.ms.outbox.sample.outbox;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
public class OutboxEvent {

    @Id
    @GeneratedValue
    @Type(type = "uuid-char")
    private UUID id;

    @NotNull
    private String aggregateType;

    @NotNull
    private String aggregateId;

    @NotNull
    private String type;

    @NotNull
    private Long timestamp;

    @NotNull
    @Column(length = 1048576) //e.g. 1 MB max
    private String payload;

    private OutboxEvent() {
    }

    public OutboxEvent(String aggregateType, String aggregateId, String type, String payload, Long timestamp) {
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public static OutboxEvent from(Outboxable event) {
        return new OutboxEvent(
                event.getAggregateType(),
                event.getAggregateId(),
                event.getType(),
                event.getPayload(),
                event.getTimestamp()
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
