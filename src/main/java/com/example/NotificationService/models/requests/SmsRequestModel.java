package com.example.NotificationService.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SmsRequestModel {

    @NotEmpty(message = "Phone Number must not be Empty and NULL")
    @Pattern(regexp = "^\\+91\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotEmpty(message = "Message must not be Empty and NULL")
    private String message;

}