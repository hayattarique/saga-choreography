package org.boot.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.event.OrderEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
public class OrderStatusPublisher {
    private final Sinks.Many<OrderEvent> orderEventSink;

    public void publish(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
        orderEventSink.tryEmitNext(orderEvent);
    }

}
