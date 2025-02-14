package com.movie.ticket.booking.system.service.entity;

public enum BookingStatus {
    /*The initial status when a booking request is submitted*/
    PENDING,
    /*The booking request has been cancelled, and reserved seats are released*/
    CANCELLED,
    /*The booking is confirmed after payement and the tickets are issued*/
    CONFIRMED,
    /*The booking was cancelled,and the refund has been processed*/
    REFUND;
}
