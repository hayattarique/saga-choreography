package org.boot.paymentservice.config;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.event.OrderEvent;
import org.boot.commondtos.event.PaymentEvent;
import org.boot.paymentservice.service.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class PaymentConsumerConfig {
    private final PaymentService paymentService;

    @Bean
    public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {


        return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
    }

    private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
        if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
            return Mono.fromSupplier(() -> paymentService.newOrderEvent(orderEvent));
        }

        return Mono.fromRunnable(() -> paymentService.cancelOrderEvent(orderEvent));
    }




}
