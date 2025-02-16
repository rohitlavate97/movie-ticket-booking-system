package com.movie.ticket.booking.system.service.broker;

import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(name = "payment-service",url = "localhost:9090")-->remove url after services registered with eureka server
@FeignClient(name = "payment-service")
public interface PaymentServiceBroker {
    @PostMapping("/payments")
    public BookingDTO makePayment(@RequestBody BookingDTO bookingDTO);
}
