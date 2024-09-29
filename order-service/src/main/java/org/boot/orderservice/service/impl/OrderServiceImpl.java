package org.boot.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.constant.PaymentStatus;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.orderservice.entities.PurchaseOrder;
import org.boot.orderservice.publisher.OrderStatusPublisher;
import org.boot.orderservice.repositories.OrderRepository;
import org.boot.orderservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusPublisher statusPublisher;

    @Override
    @Transactional
    public void cancelOrder(OrderRequestDto order) {
        Integer orderId = order.getOrderId();
        orderRepository.deleteById(orderId);
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    @Transactional
    public PurchaseOrder createOrder(OrderRequestDto order) {

        PurchaseOrder purchaseOrder = orderRepository.save(convert(order));
        order.setOrderId(purchaseOrder.getOrderId());
        //produce kafka with status ORDER_CREATED
        statusPublisher.publishOrderEvent(order, OrderStatus.ORDER_CREATED);
        return purchaseOrder;
    }


    private PurchaseOrder convert(OrderRequestDto orderRequestDto) {
        return (PurchaseOrder) Stream.of(orderRequestDto).map(
                o -> PurchaseOrder.builder().orderStatus(OrderStatus.ORDER_CREATED)
                        .paymentStatus(PaymentStatus.PAYMENT_COMPLETED)
                        .price(o.getAmount()).productId(o.getProductId()).userId(o.getUserId()).build());
    }
}
