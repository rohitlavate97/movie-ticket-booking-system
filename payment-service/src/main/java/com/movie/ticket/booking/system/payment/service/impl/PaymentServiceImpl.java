package com.movie.ticket.booking.system.payment.service.impl;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.payment.service.PaymentService;
import com.movie.ticket.booking.system.payment.service.dto.PaymentDTO;
import com.movie.ticket.booking.system.payment.service.entity.PaymentStaus;
import com.movie.ticket.booking.system.payment.service.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public void createPayment(BookingDTO bookingDTO) {
        log.info(LoggerConstants.ENTERED_SERVICE_MESSAGE.getValue(),"createPayment",
                this.getClass(),bookingDTO.toString());
        /*here we need to get bookingId,we receive bookingDto from booking-service
        feignClient call, get info and convert it into payment-service
        also modify bookingDto and add bookingId into it after bookingEntity saved in db*/
        PaymentDTO.builder()
                .bookingId(bookingDTO.getBookingId())
                .paymentAmount(bookingDTO.getBookingAmount())
                .paymentStaus(PaymentStaus.PENDING)
                .build();
        /*pass dto to payment gateway, add stripe dependency in pom.xml
        (https://dashboard.stripe.com-->Developers menu
        and enable test mode
        get the secret key)*/

    }
}
