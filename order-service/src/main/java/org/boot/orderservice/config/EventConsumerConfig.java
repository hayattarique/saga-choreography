package org.boot.orderservice.config;

import org.boot.commondtos.event.PaymentEvent;
import org.boot.orderservice.service.impl.OrderStatusUpdateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class EventConsumerConfig {
    private OrderStatusUpdateService updateService;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer() {

        return (payment) -> updateService.updateOrder(payment.getPaymentRequestDto().getOrderId(),
                po -> po.setPaymentStatus(payment.getPaymentStatus()));

    }

}
