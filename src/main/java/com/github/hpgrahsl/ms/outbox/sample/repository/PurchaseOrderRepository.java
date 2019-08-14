package com.github.hpgrahsl.ms.outbox.sample.repository;

import com.github.hpgrahsl.ms.outbox.sample.model.PurchaseOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends CrudRepository<PurchaseOrder, Long> {
}
