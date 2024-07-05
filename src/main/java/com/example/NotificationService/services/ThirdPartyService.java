package com.example.NotificationService.services;

import com.example.NotificationService.entity.sql.SmsSql;

public interface ThirdPartyService {

    void sendSms(SmsSql consumedMessage, int retryCount) throws Exception;

}
