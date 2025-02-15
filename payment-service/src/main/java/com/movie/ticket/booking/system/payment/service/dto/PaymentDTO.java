package com.movie.ticket.booking.system.payment.service.dto;

import com.movie.ticket.booking.system.payment.service.entity.PaymentStaus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {
    private UUID bookingId;
    private PaymentStaus paymentStaus;
    private Double paymentAmount;
    //private LocalDateTime paymentTimeStamp;
}
