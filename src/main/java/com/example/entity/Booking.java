package com.example.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(name = "driver_option", nullable = false)
    @Enumerated(EnumType.STRING)
    private DriverOption driverOption;

    @Column(name = "advance_deposit")
    private double advanceDeposit;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "timing", nullable = false)
    private LocalDate timing;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    public Booking(){

    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Booking(LocalDate bookingDate, Vehicle vehicle, DriverOption driverOption, long amount, LocalDate timing, Driver driver, BookingStatus status) {
        this.bookingDate = bookingDate;
        this.vehicle = vehicle;
        this.driverOption = driverOption;
        this.amount = amount;
        this.timing = timing;
        this.driver = driver;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public DriverOption getDriverOption() {
        return driverOption;
    }

    public void setDriverOption(DriverOption driverOption) {
        this.driverOption = driverOption;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getAdvanceDeposit() {
        return advanceDeposit;
    }

    public void setAdvanceDeposit(double advanceDeposit) {
        this.advanceDeposit = advanceDeposit;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDate getTiming() {
        return timing;
    }

    public void setTiming(LocalDate timing) {
        this.timing = timing;
    }



    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingDate=" + bookingDate +
                ", driverOption=" + driverOption +
                ", advanceDeposit=" + advanceDeposit +
                ", amount=" + amount +
                ", timing=" + timing +
                '}';
    }
}
