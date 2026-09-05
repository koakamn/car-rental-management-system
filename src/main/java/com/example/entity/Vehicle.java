package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "registration_number", nullable = false)
    private String registrationNumber;

    @Column(name = "model_number", nullable = false)
    private String modelNumber;

    @Column(name = "availability",nullable = false)
    @Enumerated(EnumType.STRING)
    private VehicleAvailability availability;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<Booking> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "vehicle")
    private List<Driver> drivers;

    public Vehicle(){

    }

    public Vehicle(String registrationNumber, String modelNumber, VehicleAvailability availability) {
        this.registrationNumber = registrationNumber;
        this.modelNumber = modelNumber;
        this.availability = availability;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public VehicleAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(VehicleAvailability availability) {
        this.availability = availability;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", registrationNumber=" + registrationNumber +
                ", modelNumber=" + modelNumber +
                ", availability=" + availability +
                '}';
    }
}
