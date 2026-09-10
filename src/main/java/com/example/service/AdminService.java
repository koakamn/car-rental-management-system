package com.example.service;

import com.example.entity.*;
import com.example.repository.AdminRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final UserService userService;
    private final VehicleService vehicleService;
    private final DriverService driverService;
    private final BookingService bookingService;
    private final EmployeesService employeesService;

    public AdminService(AdminRepository adminRepository, UserService userService, VehicleService vehicleService, DriverService driverService, BookingService bookingService, EmployeesService employeesService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
        this.vehicleService = vehicleService;
        this.driverService = driverService;
        this.bookingService = bookingService;
        this.employeesService = employeesService;
    }

    public Admin getAdmin(long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin with id: " + id + " not found"));
    }

    public Admin createAdmin(String name, String contactNumber, String email) {
        if (name == null || name.isBlank()) {
            throw new RuntimeException("Name is required field");
        }
        if (contactNumber == null || contactNumber.isBlank()) {
            throw new RuntimeException("Contact number is required field");
        }
        if (email == null || email.isBlank()) {
            throw new RuntimeException("email is required field");
        }
        Admin admin = new Admin(name, contactNumber, email);
        return adminRepository.save(admin);
    }

    public Admin updateAdmin(long id, String name, String contactNumber, String email) {
        Admin admin = getAdmin(id);
        if (name != null && !name.isBlank()) {
            admin.setName(name);
        }
        if (contactNumber != null && !contactNumber.isBlank()) {
            admin.setContactNumber(contactNumber);
        }
        if (email != null && !email.isBlank()) {
            admin.setEmail(email);
        }
        return adminRepository.save(admin);
    }

    public void deleteAdmin(long id) {
        Admin admin = getAdmin(id);
        adminRepository.delete(admin);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public List<Driver> getAllDrivers() {
        return driverService.getAllDrivers();
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    public List<Employees> getAllEmployees() {
        return employeesService.getAllEmployees();
    }


}
