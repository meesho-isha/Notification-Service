package com.example.NotificationService.services;

import com.example.NotificationService.entity.sql.SmsSql;
import com.example.NotificationService.models.requests.SmsRequestModel;
import com.example.NotificationService.models.response.SmsResponseModel;

public interface SmsService {

    SmsResponseModel sendSms(SmsRequestModel smsSendRequestBody);

    SmsSql findById(Long request_id) throws RuntimeException;

    void updateMessage(SmsSql consumedMessage) throws IllegalArgumentException;

}