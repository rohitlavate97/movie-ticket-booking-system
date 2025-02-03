package com.movie.ticket.booking.system.api.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class BookingControllerFallBackApi {
    @GetMapping("/booking-fallback")
    public Mono<String> bookingFallBackApi() {
        //As it is reactive gateway method return type should be Mono<String>
        return Mono.just( "Booking Service is under Maintenance");
    }
}
