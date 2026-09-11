package com.example.controller;

import com.example.entity.Booking;
import com.example.entity.DriverOption;
import com.example.entity.Employees;
import com.example.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable long id) {
        Booking booking = bookingService.getBooking(id);
        return ResponseEntity.ok(booking);
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestParam long userId,
                                                 @RequestParam long vehicleId,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate bookingDate,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate timing,
                                                 @RequestParam DriverOption driverOption,
                                                 @RequestParam(required = false) Long driverId,
                                                 @RequestParam int dailyPrice) {
        Booking booking = bookingService.createBooking(userId, vehicleId, bookingDate, timing, driverOption, driverId, dailyPrice);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(booking);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeBooking(@PathVariable long id) {
        bookingService.completeBooking(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
