package org.boot.orderservice.publisher;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.event.OrderEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
@RequiredArgsConstructor
public class OrderStatusPublisher {
    private final Sinks.Many<OrderEvent> orderSink;


    public void publishOrderEvent(OrderRequestDto requestDto, OrderStatus orderStatus) {
        OrderEvent orderEvent = new OrderEvent(requestDto, orderStatus);
        orderSink.tryEmitNext(orderEvent);
    }
}
