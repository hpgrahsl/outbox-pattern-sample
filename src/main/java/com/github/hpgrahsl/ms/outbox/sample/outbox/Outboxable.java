package com.github.hpgrahsl.ms.outbox.sample.outbox;

/**
 * Describes the main attributes of an event which should
 * get sent across service boundaries via the "outbox" table.
 *
 * @author H.P. Grahsl
 */
public interface Outboxable {

    /**
     * The id of the aggregate affected by a given event. This is also used to ensure strict
     * ordering of events belonging to one and the same aggregate.
     */
    String getAggregateId();

    /**
     * The type of the aggregate affected by a given event. This needs to be the same type string for all
     * related parts of one an the same aggregate that might get changed.
     */
    String getAggregateType();

    /**
     * The actual event payload as a valid JSON string.
     */
    String getPayload();

    /**
     * The (sub)type of an actual event which causes any changes to a specific aggregate type.
     */
    String getType();

    /**
     * The application timestamp at which the event has happened.
     */
    Long getTimestamp();
}
