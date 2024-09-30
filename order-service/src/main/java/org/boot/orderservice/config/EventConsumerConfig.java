package org.boot.orderservice.config;

import org.boot.commondtos.event.PaymentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {
    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {
        return paymentEvent -> {
        };
    }

}
