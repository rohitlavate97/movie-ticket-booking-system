package com.movie.ticket.booking.system.payment.service.impl;

import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class StripePaymentGateway {
    @Value("${stripe.key}")
    private String secretKey;
    @PostConstruct
    public void init(){
        Stripe.apiKey = secretKey;
    }
    /*for indian payment
     stripe.com/docs/testing?testing-methods=payment-methods#international-cards
     PAYMENTMETHODS ---> ASIA PACIFIC
     India(IN)-->pm_card_in
     */
    public void makePayment(BookingDTO bookingDTO){
        /*
        don't declare unnecessary variable at class level,
        also return statement takes time
        */
        //set amount,currency and payment methods
        /*copy code from https://docs.stripe.com/testing?testing-methods=
        payment-methods&testing-method=payment-methods&lang=java#international-cards*/
        try {
            /*PaymentIntentCreateParams params=new PaymentIntentCreateParams.Builder()
                .setAmount(bookingDTO.getBookingAmount().longValue())
                .setCurrency("USD")
                .setPaymentMethod("pm_card_in")
                .build();
            PaymentIntent paymentIntent=PaymentIntent.create(params);*/
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount",(int)Math.round(bookingDTO.getBookingAmount()));
//            chargeParams.put("currency","usd");
            chargeParams.put("currency","inr");
            chargeParams.put("description","Test Payment");
            chargeParams.put("source","tok_visa");
            Charge.create(chargeParams);
            bookingDTO.setBookingStatus(BookingStatus.CONFIRMED);
           /* return PaymentStaus.APPROVED;*/
        } catch (Exception e) {
            /*log.error("Payment failed at gateway level with exception"
                    +e.getStripeError().getMessage());*/
            log.error("Payment failed at gateway level with exception"
            +e.getMessage());
            /*return PaymentStaus.FAILED;*/
        }

    }
}
