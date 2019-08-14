package com.github.hpgrahsl.ms.outbox.sample.repository;

import com.github.hpgrahsl.ms.outbox.sample.outbox.OutboxEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutboxEventRepository extends CrudRepository<OutboxEvent, Long> {
}
