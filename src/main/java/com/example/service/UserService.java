package com.example.service;

import com.example.entity.User;
import com.example.entity.UserStatus;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(long id){
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User with id: " + id + " not found"));
    }

    public User createUser(String name, String contactNumber, String email, LocalDate dateOfBirth){
        if(dateOfBirth == null)
            throw new RuntimeException("Date of birth must be filled");
        int age = Period.between(dateOfBirth,LocalDate.now()).getYears();
        if(age < 18)
            throw new RuntimeException("User must be greater than 18 to drive car");
        User user = new User(name, contactNumber, email, dateOfBirth, UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    public User updateUser(long id,String name, String contactNumber, String email){
        User user = getUser(id);
        if(name != null && !name.isBlank()) {
            user.setName(name);
        }
        if(contactNumber != null && !contactNumber.isBlank()){
            user.setContactNumber(contactNumber);
        }
        if(email != null && !email.isBlank()){
            user.setEmail(email);
        }
        userRepository.save(user);
        return user;
    }

    public boolean isUserActive(long id){
        return getUser(id).getUserStatus() == UserStatus.ACTIVE;
    }

    public void changeUserStatus(long id, UserStatus status){
        User user = getUser(id);
        user.setUserStatus(status);
        userRepository.save(user);
    }
}
