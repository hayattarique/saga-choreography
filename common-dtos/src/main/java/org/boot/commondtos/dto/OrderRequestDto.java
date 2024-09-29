package org.boot.commondtos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private Integer userId;
    private Integer productId;
    private Integer orderId;
    private String productName;
    private double amount;

}
