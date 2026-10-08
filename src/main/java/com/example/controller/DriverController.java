package com.example.controller;

import com.example.entity.Driver;
import com.example.entity.Employees;
import com.example.service.DriverService;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable long id) {
        Driver driver = driverService.getDriver(id);
        return ResponseEntity.ok(driver);
    }

    @GetMapping
    public ResponseEntity<List<Driver>> getAllDrivers(){
        List<Driver> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    @PostMapping
    public ResponseEntity<Driver> createDriver(@RequestParam @Size(max = 20) String name,
                                               @RequestParam @Size(max = 12) String contactNumber,
                                               @RequestParam int age,
                                               @RequestParam String residence,
                                               @RequestParam String driverLicenseNumber) {
        Driver createdDriver = driverService.createDriver(name, contactNumber, age, residence, driverLicenseNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdDriver);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Driver> updateDriver(@PathVariable long id,
                                               @RequestParam(required = false) @Size(max = 12) String contactNumber,
                                               @RequestParam(required = false) String residence,
                                               @RequestParam(required = false) String driverLicenseNumber) {
        Driver updatedDriver = driverService.updateDriver(id, contactNumber, residence, driverLicenseNumber);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable long id) {
        driverService.deleteDriver(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<Boolean> getAvailability(@PathVariable long id) {
        boolean isAvailable = driverService.isDriverAvailable(id);
        return ResponseEntity.ok(isAvailable);
    }

    @GetMapping("/fee")
    public ResponseEntity<Double> getDriverFee(@RequestParam int rentalDays) {
        double driverFee = driverService.calculateDriverFee(rentalDays);
        return ResponseEntity.ok(driverFee);
    }
}
