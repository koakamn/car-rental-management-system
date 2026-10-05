package com.example.service;

import com.example.entity.*;
import com.example.repository.DriverRepository;
import com.example.repository.EmployeesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {
    private final EmployeesRepository employeesRepository;
    private final BookingService bookingService;
    private final DriverService driverService;
    private final DriverRepository driverRepository;

    public EmployeesService(EmployeesRepository employeesRepository, BookingService bookingService, DriverService driverService, DriverRepository driverRepository) {
        this.employeesRepository = employeesRepository;
        this.bookingService = bookingService;
        this.driverService = driverService;
        this.driverRepository = driverRepository;
    }

    public Employees getEmployee(long id) {
        return employeesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee with id: " + id + " not found"));
    }

    public List<Employees> getAllEmployees() {
        return employeesRepository.findAll();
    }

    public Employees createEmployee(String email, String name, String contactNumber) {
        if (email == null || email.isBlank()) {
            throw new RuntimeException("email is required field");
        }
        if (name == null || name.isBlank()) {
            throw new RuntimeException("name is required field");
        }
        if (contactNumber == null || contactNumber.isBlank()) {
            throw new RuntimeException("contact number is required field");
        }
        Employees employee = new Employees(email, name, contactNumber);
        return employeesRepository.save(employee);
    }


    public Employees updateEmployee(long id, String email, String name, String contactNumber) {
        Employees employee = getEmployee(id);
        if (name != null && !name.isBlank()) {
            employee.setName(name);
        }
        if (contactNumber != null && !contactNumber.isBlank()) {
            employee.setContactNumber(contactNumber);
        }
        if (email != null && !email.isBlank()) {
            employee.setEmail(email);
        }
        return employeesRepository.save(employee);
    }

    public void deleteEmployee(long id) {
        Employees employee = getEmployee(id);
        if(driverRepository.existsByEmployeesId(id)){
            throw new RuntimeException("Cannot delete this employee");
        }
        employeesRepository.delete(employee);
    }

    @Transactional
    public void assignDriverToBooking(long driverId, long bookingId) {
        Booking booking = bookingService.getBooking(bookingId);

        if (!(booking.getStatus() == BookingStatus.ACTIVE)) {
            throw new RuntimeException("The booking is non-active now!");
        }
        if (booking.getDriverOption() != DriverOption.WITH_DRIVER) {
            throw new RuntimeException("Booking is self-drive, driver is not needed");
        }
        Driver driver = driverService.getDriver(driverId);
        Driver bookingDriver = booking.getDriver();
        if (driver.equals(bookingDriver)) {
            return;
        }
        if (bookingDriver != null && !(driver.getId() == bookingDriver.getId())) {
            driverService.assignVehicleToDriver(bookingDriver.getId(), null);
        }
        if (driverService.isDriverAvailable(driverId)) {
            driverService.assignVehicleToDriver(driverId, booking.getVehicle());
            bookingService.assignDriverToBooking(bookingId, driver);
        } else {
            throw new RuntimeException("Driver is not available");
        }
    }
}
