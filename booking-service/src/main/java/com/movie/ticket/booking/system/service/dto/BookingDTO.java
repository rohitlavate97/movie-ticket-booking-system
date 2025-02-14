package com.movie.ticket.booking.system.service.dto;

import com.movie.ticket.booking.system.service.entity.BookingStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDTO {
    //same fields from entity
    private UUID bookingId;
    @NotNull(message = "UserId is mandatory")
    private String userId;
    @NotNull(message = "movieId is mandatory")
    private Integer movieId;
    @NotNull(message = "User must have booked at least 1 seat")
    private List<String> seatsBooked;
    @NotNull(message = "show date is mandatory")
    private LocalDate showDate;
    @NotNull(message = "show time is mandatory")
    private LocalTime showTime;
    @Positive(message = "amount must be greater than 0")
    @NotNull(message = "booking amount is mandatory")
    private Double bookingAmount;
    private BookingStatus bookingStatus;
}
