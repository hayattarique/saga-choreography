package org.boot.commondtos.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.boot.commondtos.dto.OrderRequestDto;
import org.boot.commondtos.constant.OrderStatus;

import java.time.LocalDate;
import java.util.UUID;
@NoArgsConstructor
@Data
public class OrderEvent implements Event {
    private UUID eventId = UUID.randomUUID();
    private LocalDate eventDate = LocalDate.now();
    private OrderRequestDto orderRequestDto;
    private OrderStatus orderStatus;

    public OrderEvent(OrderRequestDto orderRequestDto, OrderStatus orderStatus) {
        this.orderRequestDto = orderRequestDto;
        this.orderStatus = orderStatus;
    }

    @Override
    public LocalDate getDate() {
        return eventDate;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }
}
