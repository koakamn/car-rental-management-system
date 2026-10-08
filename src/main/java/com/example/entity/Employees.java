package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "employees")
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Name can not be empty")
    @Size(max = 40, message = "Name must be up to 40 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    @Size(max = 40, message = "Email must be up to 40 characters")
    @Column(name = "email_address")
    private String email;

    @NotBlank(message = "Contact number can not be empty")
    @Size(max = 12, message = "Contact must be up to 12 characters")
    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "designation")
    private String designation;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    @JsonIgnore
    private Admin admin;

    @OneToMany(mappedBy = "employees")
    @JsonIgnore
    private List<Driver> drivers;


    public Employees() {
    }

    public Employees(String name, String email, String designation, String contactNumber) {
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.contactNumber = contactNumber;
    }

    public Employees(String email, String name, String contactNumber) {
        this.email = email;
        this.name = name;
        this.contactNumber = contactNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return "Employees{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
