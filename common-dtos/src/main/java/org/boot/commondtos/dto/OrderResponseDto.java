package org.boot.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.boot.commondtos.constant.OrderStatus;
import org.boot.commondtos.constant.PaymentStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private Integer userId;
    private Integer productId;
    private String productName;
    private double amount;
    private Integer orderId;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
}
