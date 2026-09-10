package com.example.service;

import com.example.entity.*;
import com.example.repository.BookingRepository;
import com.example.repository.DriverRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class BookingService {
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final DriverRepository driverRepository;

    public BookingService(BookingRepository bookingRepository, UserService userService, VehicleService vehicleService, DriverService driverService, DriverRepository driverRepository) {
        this.bookingRepository = bookingRepository;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.driverRepository = driverRepository;
    }

    public Booking getBooking(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking with id:" + id + " not found"));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Transactional
    public Booking createBooking(long userId, long vehicleId, LocalDate bookingDate, LocalDate timing, DriverOption driverOption, Long driverId, int dailyPrice) {
        double driverFee = 0.0;
        Driver driver = null;
        User user = userService.getUser(userId);
        Vehicle vehicle = vehicleService.getVehicle(vehicleId);

        if (bookingDate == null || bookingDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Booking date can not be in the past");
        }

        if (!userService.isUserActive(userId)) {
            throw new RuntimeException("User is not active now");
        }


        if (!vehicleService.checkAvailability(vehicleId)) {
            throw new RuntimeException("Vehicle is not available now");
        }

        if (driverOption == DriverOption.WITH_DRIVER) {
            if (driverId == null) {
                throw new RuntimeException("Driver ID is required");
            }
            if (!driverService.isDriverAvailable(driverId)) {
                throw new RuntimeException("Driver is not available");
            } else {
                driverFee = driverService.calculateDriverFee(1);
                driver = driverService.getDriver(driverId);
                driver.setVehicle(vehicle);
                driverRepository.save(driver);
            }
        } else {
            driverFee = 0;
        }

        int totalAmount = (int) (dailyPrice + driverFee);

        Booking booking = new Booking(bookingDate, vehicle, driverOption, totalAmount, timing, driver);
        double advanceDeposit = totalAmount * 0.20;
        booking.setAdvanceDeposit(advanceDeposit);
        booking.setUser(user);
        Booking savedBooking = bookingRepository.save(booking);
        vehicleService.markAsRented(vehicleId);
        return savedBooking;
    }

    private void setVehicleToExistDriver(Booking booking) {
        Driver driver = booking.getDriver();
        if (driver != null) {
            driver.setVehicle(null);
            driverRepository.save(driver);
        }
    }

    @Transactional
    public void cancelBooking(long bookingId) {
        Booking booking = getBooking(bookingId);
//        Driver driver = booking.getDriver();
//        if (driver != null){
//            driver.setVehicle(null);
//            driverRepository.save(driver);
//        }
        setVehicleToExistDriver(booking);
        long vehicleId = booking.getVehicle().getId();
        vehicleService.markAsAvailable(vehicleId);
        bookingRepository.delete(booking);
    }

    @Transactional
    public void completeBooking(long bookingId) {
        Booking booking = getBooking(bookingId);
        //        Driver driver = booking.getDriver();
//        if (driver != null){
//            driver.setVehicle(null);
//            driverRepository.save(driver);
//        }
        setVehicleToExistDriver(booking);
        long vehicleId = booking.getVehicle().getId();
        vehicleService.markAsAvailable(vehicleId);
    }

    public void assignDriverToBooking(long bookingId, Driver driver) {
        Booking booking = getBooking(bookingId);
        booking.setDriver(driver);
        bookingRepository.save(booking);
    }
}



