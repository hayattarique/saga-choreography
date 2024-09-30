package org.boot.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.constant.PaymentStatus;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.orderservice.entities.PurchaseOrder;
import org.boot.orderservice.publisher.OrderStatusPublisher;
import org.boot.orderservice.repositories.OrderRepository;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class OrderStatusUpdateService {
    private final OrderRepository orderRepository;
    private final OrderStatusPublisher statusPublisher;

    @Transactional
    public void updateOrder(int orderId, Consumer<PurchaseOrder> consumer) {
        orderRepository.findById(orderId).ifPresent(consumer.andThen(this::updateOrder));
    }

    private void updateOrder(PurchaseOrder purchaseOrder) {

        boolean isPaymentCompleted = purchaseOrder.getPaymentStatus().equals(PaymentStatus.PAYMENT_COMPLETED);

        OrderStatus orderStatus = isPaymentCompleted ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        purchaseOrder.setOrderStatus(orderStatus);
        if (!isPaymentCompleted) {
            statusPublisher.publishOrderEvent(convertEntityToDto(purchaseOrder), orderStatus);
        }

    }

    private OrderRequestDto convertEntityToDto(PurchaseOrder purchaseOrder) {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setOrderId(purchaseOrder.getOrderId());
        orderRequestDto.setUserId(purchaseOrder.getUserId());
        orderRequestDto.setAmount(purchaseOrder.getPrice());
        orderRequestDto.setProductId(purchaseOrder.getProductId());
        return orderRequestDto;
    }
}
