package com.movie.ticket.booking.system.payment.service.controller;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentAPI {
    @Autowired
    private PaymentService paymentService;
    @GetMapping
    public String test() {
        return "Payment successful";
    }
    @PostMapping
    public BookingDTO makePayment(@RequestBody BookingDTO bookingDTO) {
        log.info(LoggerConstants.ENTERED_CONTROLLER_MESSAGE.getValue(),"makePayment",
                this.getClass(),bookingDTO.toString());
        return this.paymentService.makePayment(bookingDTO);
    }
}
