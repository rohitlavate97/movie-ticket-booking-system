package com.movie.ticket.booking.system.payment.service.entity;

public enum PaymentStaus {
    /*The booking request is pending until payment confirmation*/
    PENDING,
    /*Payment has been successfully processed and confirmed*/
    APPROVED,
    /*Payment was successfully refunded for cancelled booking*/
    REFUND,
    /*Payment processing failed and the user needs to retry the payment*/
    FAILED;
}
