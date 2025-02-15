package com.movie.ticket.booking.system.payment.service.impl;

import com.movie.ticket.booking.system.payment.service.entity.PaymentStaus;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

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
    public PaymentStaus makePayment(Double paymentAmount){
        //set amount,currency and payment methods
        try {
            PaymentIntentCreateParams params=new PaymentIntentCreateParams.Builder()
                .setAmount(paymentAmount.longValue())
                .setCurrency("USD")
                .setPaymentMethod("pm_card_in")
                .build();
            PaymentIntent paymentIntent=PaymentIntent.create(params);
            return PaymentStaus.APPROVED;
        } catch (StripeException e) {
            log.error("Payment failed at gateway level with exception"
                    +e.getStripeError().getMessage());
            return PaymentStaus.FAILED;
        }

    }
}
