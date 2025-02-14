package com.movie.ticket.booking.system.service.controller;

import com.movie.ticket.booking.system.service.broker.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.dto.BookingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@Slf4j
/*
@Slf4j it logs controller, controller has been invoked?,
successfully completed ?,
which service it going to cal
*/
public class BookingAPI {
    @Autowired
    private PaymentServiceBroker paymentServiceBroker;
    /*
    REST Principle: method name has to be action
    */
    @GetMapping("/test")
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        log.info("Entered {} class", BookingAPI.class);
        return null;
    }
}
