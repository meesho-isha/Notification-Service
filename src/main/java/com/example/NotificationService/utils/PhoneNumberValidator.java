package com.example.NotificationService.utils;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhoneNumberValidator {

    public boolean validateOne(String phoneNumber) throws RuntimeException {
        if(phoneNumber == null) {
            throw new RuntimeException("Phone number should not be null");
        }
        return phoneNumber.matches("^\\+91\\d{10}$");
    }

    public boolean validateList(List<String> phoneNumbers) throws RuntimeException {
        if(phoneNumbers == null || phoneNumbers.isEmpty()) {
            throw new RuntimeException("Phone number should not be null");
        }
        for(String phoneNumber : phoneNumbers) {
            if(!validateOne(phoneNumber)) {
                throw new RuntimeException("Phone number should not be null");
            }
        }
        return true;
    }

}
