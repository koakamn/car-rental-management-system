package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NotBlank(message = "Name can not be empty")
    @Size(max = 20, message = "Name must be up to 20 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "Contact number can not be empty")
    @Size(max = 12, message = "Contact must be up to 12 characters")
    @Column(name = "contact_number")
    private String contactNumber;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
    @Size(max = 50, message = "Email must be up to 50 characters")
    @Column(name = "email_address")
    private String email;

    @Column(name = "gender")
    private String gender;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<User> user;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private List<Employees> employees;

    public Admin() {
    }

    public Admin(String name, String contactNumber, String email, String gender) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.gender = gender;
    }

    public Admin(String name, String contactNumber, String email) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Employees> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
