package org.boot.commondtos.dto;

import org.boot.commondtos.constant.OrderStatus;

public class OrderResponseDto {
    private Integer userId;
    private Integer productId;
    private String productName;
    private double amount;
    private Integer orderId;
    private OrderStatus status;
}
