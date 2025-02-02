package com.movie.ticket.booking.system.service.controller;

import com.movie.ticket.booking.system.service.broker.PaymentServiceBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
public class BookingAPI {
    @Autowired
    private PaymentServiceBroker paymentServiceBroker;
    @GetMapping("/test")
    public String test() {
        return this.paymentServiceBroker.test();
    }
}
