package com.movie.ticket.booking.system.payment.service;

import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.payment.service.dto.PaymentDTO;
import org.springframework.web.bind.annotation.RequestBody;

public interface PaymentService {
    public BookingDTO makePayment(BookingDTO bookingDTO);
}
