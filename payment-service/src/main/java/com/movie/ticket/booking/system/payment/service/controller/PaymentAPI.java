package com.movie.ticket.booking.system.payment.service.controller;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentAPI {
    @GetMapping
    public String test() {
        return "Payment successful";
    }
    @PostMapping
    public void createPayment(@RequestBody BookingDTO bookingDTO) {
        log.info(LoggerConstants.ENTERED_CONTROLLER_MESSAGE.getValue(),"createPayment",
                this.getClass(),bookingDTO.toString());

    }
}
