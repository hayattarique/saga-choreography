package org.boot.commondtos.event;

import lombok.NoArgsConstructor;
import org.boot.commondtos.constant.PaymentStatus;
import org.boot.commondtos.dto.PaymentRequestDto;

import java.time.LocalDate;
import java.util.UUID;
@NoArgsConstructor
public class PaymentEvent implements Event {
    public PaymentEvent(PaymentStatus paymentStatus, PaymentRequestDto paymentRequestDto) {
        this.paymentStatus = paymentStatus;
        this.paymentRequestDto = paymentRequestDto;
    }

    private UUID eventId = UUID.randomUUID();
    private LocalDate eventDate = LocalDate.now();
    private PaymentRequestDto paymentRequestDto;
    private PaymentStatus paymentStatus;

    @Override
    public LocalDate getDate() {
        return eventDate;
    }

    @Override
    public UUID getEventId() {
        return eventId;
    }
}
