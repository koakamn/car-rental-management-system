package com.example.service;

import com.example.entity.*;
import com.example.repository.BookingRepository;
import com.example.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final BookingRepository bookingRepository;

    @Autowired
    public DriverService(DriverRepository driverRepository, BookingRepository bookingRepository) {
        this.driverRepository = driverRepository;
        this.bookingRepository = bookingRepository;
    }

    public Driver getDriver(long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver with id: " + id + " not found"));
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

    public Driver createDriver(String name, String contactNumber, int age, String residence, String driverLicenseNumber) {
        if (age < 18)
            throw new RuntimeException("You must be older 18 to drive car");
        Driver driver = new Driver(name, contactNumber, age, residence, driverLicenseNumber);
        return driverRepository.save(driver);
    }

    public Driver updateDriver(long id, String contactNumber, String residence, String driverLicenseNumber) {
        Driver driver = getDriver(id);
        if (contactNumber != null && !contactNumber.isBlank()) {
            driver.setContactNumber(contactNumber);
        }
        if (residence != null && !residence.isBlank()) {
            driver.setResidence(residence);
        }
        if (driverLicenseNumber != null && !driverLicenseNumber.isBlank()) {
            driver.setDriverLicenseNumber(driverLicenseNumber);
        }
        return driverRepository.save(driver);
    }

    public void deleteDriver(long id) {
        Driver driver = getDriver(id);
        if (driver.getVehicle() != null) {
            throw new RuntimeException("Driver is already have rented");
        }
        if(bookingRepository.existsByDriverId(id)){
            throw new RuntimeException("Cannot delete driver: it has existing bookings");
        }
        driverRepository.delete(driver);

    }

    public double calculateDriverFee(long rentalDays) {
        double driverDailyRate = 25.0;
        return rentalDays * driverDailyRate;
    }

    public boolean isDriverAvailable(long id) {
        Driver driver = getDriver(id);
        return driver.getVehicle() == null;
    }

    public void assignVehicleToDriver(long driverId, Vehicle vehicle) {
        Driver driver = getDriver(driverId);
        driver.setVehicle(vehicle);
        driverRepository.save(driver);
    }
}
