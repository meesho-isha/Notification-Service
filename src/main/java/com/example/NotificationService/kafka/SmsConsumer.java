package com.example.NotificationService.kafka;

import com.example.NotificationService.constants.SmsStatus;
import com.example.NotificationService.entity.sql.SmsSql;
import com.example.NotificationService.services.BlacklistService;
import com.example.NotificationService.services.SmsService;
import com.example.NotificationService.services.ThirdPartyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class SmsConsumer {

    private final SmsService smsService;
    private final ThirdPartyService thirdPartyService;
    private final BlacklistService blacklistService;

    @Autowired
    public SmsConsumer(SmsService smsService, ThirdPartyService thirdPartyService, BlacklistService blacklistService) {
        this.smsService = smsService;
        this.thirdPartyService = thirdPartyService;
        this.blacklistService = blacklistService;
    }

    @KafkaListener(topics = "notifications", groupId = "group_id")
    public void consume(Long message_id, @Header("retryCount") int retryCount) throws Exception {
        log.info("Consuming message_id: " + message_id + " retryCount: " + retryCount);
        SmsSql smsSql = smsService.findById(message_id);

        boolean isBlacklisted = blacklistService.isBlacklisted(smsSql.getPhoneNumber());

        if (isBlacklisted) {
            log.info(String.format("Sending message failed as phone number %s is blacklisted",smsSql.getPhoneNumber()));
            handleBlacklistedMessage(smsSql);
        } else {
            log.info("Sending message to third party API: {}", smsSql.getMessage());
            thirdPartyService.sendSms(smsSql, retryCount);
        }
    }

    public void handleBlacklistedMessage(SmsSql smsSql) {
        smsSql.setStatus(SmsStatus.FAILED);
        smsSql.setFailureCode(String.valueOf(HttpStatus.BAD_REQUEST));
        smsSql.setFailureComments("Phone number is blacklisted");
        smsService.updateMessage(smsSql);
    }

}
