package com.movie.ticket.booking.system.payment.service.impl;

import com.movie.ticket.booking.system.commons.constants.LoggerConstants;
import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import com.movie.ticket.booking.system.payment.service.PaymentService;
import com.movie.ticket.booking.system.payment.service.entity.PaymentEntity;
import com.movie.ticket.booking.system.payment.service.entity.PaymentStaus;
import com.movie.ticket.booking.system.payment.service.repository.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private StripePaymentGateway stripePaymentGateway;
    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    @Transactional
    public BookingDTO makePayment(BookingDTO bookingDTO) {
        log.info(LoggerConstants.ENTERED_SERVICE_MESSAGE.getValue(),"createPayment",
                this.getClass(),bookingDTO.toString());
        /*here we need to get bookingId,we receive bookingDto from booking-service
        feignClient call, get info and convert it into payment-service
        also modify bookingDto and add bookingId into it after bookingEntity saved in db*/
        PaymentEntity paymentEntity=PaymentEntity.builder()
                .bookingId(bookingDTO.getBookingId())
                .paymentAmount(bookingDTO.getBookingAmount())
                .paymentStaus(PaymentStaus.PENDING)
                .build();
        /*pass dto to payment gateway, add stripe dependency in pom.xml
        (https://dashboard.stripe.com-->Developers menu
        and enable test mode
        get the secret key)*/
        this.paymentRepository.save(paymentEntity);
        /*make call to payment-gateway*/
        this.stripePaymentGateway.makePayment(bookingDTO);
        paymentEntity.setPaymentTimeStamp(LocalDateTime.now());
        if(bookingDTO.getBookingStatus().equals(BookingStatus.CONFIRMED)){
            /*also we can check paymentDTO,getPaymentStatus().equals("APPROVED") and change bookingstatus
            accordingly*/
            paymentEntity.setPaymentStaus(PaymentStaus.APPROVED);
            /*bookingDTO.setBookingStatus(BookingStatus.CONFIRMED);*/
        }else{
            paymentEntity.setPaymentStaus(PaymentStaus.FAILED);
            bookingDTO.setBookingStatus(BookingStatus.CANCELLED);
        }
        return bookingDTO;
    }
}
