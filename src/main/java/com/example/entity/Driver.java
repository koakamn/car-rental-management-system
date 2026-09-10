package com.example.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "contact_number", nullable = false, length = 12)
    private String contactNumber;

    @Column(name = "age")
    private int age;

    @Column(name = "driving_license", nullable = false)
    private String driverLicenseNumber;

    @Column(name = "residence")
    private String residence;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employees employees;


    public Driver(){

    }

    public Driver(String name, String contactNumber, int age, String residence, String driverLicenseNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.age = age;
        this.residence = residence;
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public Driver(String name, String contactNumber, String driverLicenseNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDriverLicenseNumber() {
        return driverLicenseNumber;
    }

    public void setDriverLicenseNumber(String driverLicenseNumber) {
        this.driverLicenseNumber = driverLicenseNumber;
    }

    public String getResidence() {
        return residence;
    }

    public Employees getEmployees() {
        return employees;
    }

    public void setEmployees(Employees employees) {
        this.employees = employees;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Driver driver)) return false;
        return id == driver.id && age == driver.age && Objects.equals(name, driver.name) && Objects.equals(contactNumber, driver.contactNumber) && Objects.equals(driverLicenseNumber, driver.driverLicenseNumber) && Objects.equals(residence, driver.residence) && Objects.equals(vehicle, driver.vehicle) && Objects.equals(employees, driver.employees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, contactNumber, age, driverLicenseNumber, residence, vehicle, employees);
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", age=" + age +
                ", driverLicenseNumber=" + driverLicenseNumber +
                ", residence='" + residence + '\'' +
                '}';
    }
}
