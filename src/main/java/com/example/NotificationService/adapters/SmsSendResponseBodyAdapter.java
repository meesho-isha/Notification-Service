package com.example.NotificationService.adapters;

import com.example.NotificationService.models.response.SmsResponseModel;
import org.springframework.stereotype.Component;

@Component
public class SmsSendResponseBodyAdapter {

    public static SmsResponseModel createResponseBody(Long requestId, String comments) {
        return SmsResponseModel.builder()
                .requestId(requestId)
                .comments(comments)
                .build();
    }

}
