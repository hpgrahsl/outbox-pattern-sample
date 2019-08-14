package com.github.hpgrahsl.ms.outbox.sample.controller;

import com.github.hpgrahsl.ms.outbox.sample.OrderService;
import com.github.hpgrahsl.ms.outbox.sample.model.dto.OrderDTO;
import com.github.hpgrahsl.ms.outbox.sample.model.dto.OrderLineStatusUpdateDTO;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
public class OrderResource {

    private final OrderService service;

    public OrderResource(OrderService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<OrderDTO> getOrders() {
        return Flux.fromIterable(service.findOrders()).map(OrderDTO::from);
    }

    @PostMapping
    public Mono<OrderDTO> postOrder(@RequestBody OrderDTO order) {
        return Mono.just(service.placeOrder(OrderDTO.to(order))).map(OrderDTO::from);
    }

    @PutMapping("/{orderId}/lines/{orderLineId}")
    public Mono<OrderDTO> updateOrderLine(@PathVariable("orderId") long orderId,
                                          @PathVariable("orderLineId") long orderLineId,
                                          @RequestBody OrderLineStatusUpdateDTO orderLine) {
        return Mono.just(service.updateOrderLineStatus(orderId, orderLineId, orderLine.getNewStatus()))
                .map(OrderDTO::from);
    }

}
