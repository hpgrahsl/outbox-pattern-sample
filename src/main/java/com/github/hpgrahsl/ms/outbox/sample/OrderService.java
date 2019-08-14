package com.github.hpgrahsl.ms.outbox.sample;

import com.github.hpgrahsl.ms.outbox.sample.model.PurchaseOrder;
import com.github.hpgrahsl.ms.outbox.sample.event.OrderLineUpdatedEvent;
import com.github.hpgrahsl.ms.outbox.sample.model.EntityNotFoundException;
import com.github.hpgrahsl.ms.outbox.sample.model.OrderLineStatus;
import com.github.hpgrahsl.ms.outbox.sample.repository.PurchaseOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private ApplicationEventPublisher eventBus;

    private PurchaseOrderRepository repository;

    public OrderService(ApplicationEventPublisher eventBus, PurchaseOrderRepository repository) {
        this.eventBus = eventBus;
        this.repository = repository;
    }

    @Transactional
    public PurchaseOrder placeOrder(PurchaseOrder order) {
        repository.save(order);
        return order;
    }

    @Transactional
    public PurchaseOrder updateOrderLineStatus(long orderId, long orderLineId, OrderLineStatus newStatus) {
        PurchaseOrder po = repository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("order with id " + orderId + " doesn't exist!"));
        OrderLineStatus oldStatus = po.updateOrderLine(orderLineId, newStatus);
        eventBus.publishEvent(OrderLineUpdatedEvent.of(orderId, orderLineId, newStatus, oldStatus));
        repository.save(po);
        return po;
    }

    public Iterable<PurchaseOrder> findOrders() {
        return repository.findAll();
    }

}
