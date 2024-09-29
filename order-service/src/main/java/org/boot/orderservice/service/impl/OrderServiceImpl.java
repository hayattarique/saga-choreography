package org.boot.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.constant.PaymentStatus;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.dto.OrderResponseDto;
import org.boot.orderservice.entities.PurchaseOrder;
import org.boot.orderservice.publisher.OrderStatusPublisher;
import org.boot.orderservice.repositories.OrderRepository;
import org.boot.orderservice.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusPublisher statusPublisher;

    @Override
    @Transactional
    public void cancelOrder(int orderId) {

        orderRepository.deleteById(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrders() {
        List<PurchaseOrder> orderList = orderRepository.findAll();
        return orderList.stream().map(this::convert).toList();
    }

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto order) {
        PurchaseOrder purchaseOrder = orderRepository.save(convertToEntity(order));
        OrderResponseDto orderResponseDto = convert(purchaseOrder);
        //produce kafka with status ORDER_CREATED
        statusPublisher.publishOrderEvent(order, OrderStatus.ORDER_CREATED);
        return orderResponseDto;
    }

    private OrderResponseDto convert(PurchaseOrder order) {
        return OrderResponseDto.builder().orderId(order.getOrderId()).amount(order.getPrice())
                .productName(order.getProductName()).orderStatus(order.getOrderStatus())
                .paymentStatus(order.getPaymentStatus()).productId(order.getProductId())
                .userId(order.getUserId()).build();
    }


    private PurchaseOrder convertToEntity(OrderRequestDto orderRequestDto) {
        return PurchaseOrder.builder().orderStatus(OrderStatus.ORDER_CREATED)
                .paymentStatus(PaymentStatus.PAYMENT_COMPLETED).price(orderRequestDto.getAmount())
                .userId(orderRequestDto.getUserId()).productId(orderRequestDto.getProductId())
                .productName(orderRequestDto.getProductName()).build();
    }
}
