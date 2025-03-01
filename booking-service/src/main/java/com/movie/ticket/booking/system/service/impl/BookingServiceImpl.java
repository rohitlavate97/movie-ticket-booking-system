package com.movie.ticket.booking.system.service.impl;

import com.movie.ticket.booking.system.commons.dto.BookingDTO;
import com.movie.ticket.booking.system.commons.dto.BookingStatus;
import com.movie.ticket.booking.system.service.BookingService;
import com.movie.ticket.booking.system.service.broker.PaymentServiceBroker;
import com.movie.ticket.booking.system.service.entity.BookingEntity;
import com.movie.ticket.booking.system.service.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private PaymentServiceBroker paymentServiceBroker;
    @Autowired
    private BookingRepository bookingRepository;
    /*
    We cannot use record class, they are singleton classes, before sending
    request to payment-service we must sotre status as PENDING in db
    */
    @Transactional
    @Override
    public BookingDTO createBooking(BookingDTO bookingDTO) {
        //get data from bookingDTO to pass into Entity object so that we can save it
        BookingEntity bookingEntity = BookingEntity.builder()
                .bookingAmount(bookingDTO.getBookingAmount())
                .userId(bookingDTO.getUserId())
                .movieId(bookingDTO.getMovieId())
                .seatsBooked(bookingDTO.getSeatsBooked())
                .showDate(bookingDTO.getShowDate())
                .showTime(bookingDTO.getShowTime())
                .bookingStatus(BookingStatus.PENDING)
                .build();
        this.bookingRepository.save(bookingEntity);
        /*modify bookingDto before sending it to payment-service*/
        bookingDTO.setBookingId(bookingEntity.getBookingId());
        /*setting bookingstatus in dto before sending to payment-service to avoid NullPointerException*/
        bookingDTO.setBookingStatus(BookingStatus.PENDING);
        //call the payment-service
        bookingDTO=this.paymentServiceBroker.makePayment(bookingDTO);
        /*set new status to existing entity which is stored in db*/
        bookingEntity.setBookingStatus(bookingDTO.getBookingStatus());
        /*If method is annotated with @Transactional we don't need to do multiple save
        this.bookingRepository.save(bookingEntity);
        */
        return BookingDTO.builder()
                .bookingId(bookingEntity.getBookingId())
                .bookingAmount(bookingEntity.getBookingAmount())
                /*changing status to CONFIRMED from bookingEntity*/
                .bookingStatus(bookingEntity.getBookingStatus())
                .movieId(bookingEntity.getMovieId())
                .showDate(bookingEntity.getShowDate())
                .showTime(bookingEntity.getShowTime())
                .bookingAmount(bookingEntity.getBookingAmount())
                .userId(bookingEntity.getUserId())
                .seatsBooked(bookingEntity.getSeatsBooked())
                .build();
    }
}
