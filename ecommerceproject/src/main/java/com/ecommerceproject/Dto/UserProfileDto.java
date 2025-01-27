package com.ecommerceproject.Dto;

import com.ecommerceproject.Entity.User;
import com.ecommerceproject.Enum.Gender;
import jakarta.persistence.Column;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserProfileDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String phoneNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfileDto(User u){
        this.username=u.getUsername();
        this.email=u.getEmail();
        this.firstName=u.getProfile().getFirstName();
        this.lastName=u.getProfile().getLastName();
        this.dateOfBirth=u.getProfile().getDateOfBirth();
        this.gender=u.getProfile().getGender();
        this.phoneNumber=u.getProfile().getPhoneNumber();
        this.createdAt=u.getCreatedAt();
        this.updatedAt=u.getUpdatedAt();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
