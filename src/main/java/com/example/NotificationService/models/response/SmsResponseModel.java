package com.example.NotificationService.models.response;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class SmsResponseModel {

    private Long requestId;
    private String comments;

}
