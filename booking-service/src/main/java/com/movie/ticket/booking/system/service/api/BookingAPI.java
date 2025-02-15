package com.movie.ticket.booking.system.service.api;

import com.movie.ticket.booking.system.service.BookingService;
import com.movie.ticket.booking.system.service.constants.LoggerConstants;
import com.movie.ticket.booking.system.service.dto.BookingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/*
@Slf4j it logs controller, controller has been invoked?,
successfully completed ?,
which service it going to cal
*/
@RestController
@RequestMapping("/bookings")
@Slf4j
public class BookingAPI {
    @Autowired
    private BookingService bookingService;
    /*
    REST Principle: method name has to be action
    */
    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        log.info(LoggerConstants.ENTERED_CONTROLLER_MESSAGE.getValue(), "createBooking",
                this.getClass(), bookingDTO.toString());
        return this.bookingService.createBooking(bookingDTO);
    }
}
