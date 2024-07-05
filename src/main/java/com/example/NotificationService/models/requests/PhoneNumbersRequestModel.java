package com.example.NotificationService.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PhoneNumbersRequestModel {

    @NotNull(message = "Phone Numbers parameter missing")
    private List<String> phoneNumbers;

}
