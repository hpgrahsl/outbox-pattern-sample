package com.github.hpgrahsl.ms.outbox.sample.outbox;

import com.github.hpgrahsl.ms.outbox.sample.repository.OutboxEventRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class OutboxListener {

    private OutboxEventRepository repository;

    public OutboxListener(OutboxEventRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void onExportedEvent(Outboxable event) {

        OutboxEvent outboxEvent = OutboxEvent.from(event);

        // The outbox event will be written to the "outbox" table and immediately removed afterwards
        // removed again. Thus the outbox table is effectively empty all the time. From a CDC perspective
        // this will produce an INSERT operation followed by a DELETE operation of the same record such
        // that both events will be captured from the database log by Debezium.
        repository.save(outboxEvent);
        repository.delete(outboxEvent);

    }

}
