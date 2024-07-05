package com.example.NotificationService.services.Impl;

import com.example.NotificationService.adapters.SmsSendResponseBodyAdapter;
import com.example.NotificationService.adapters.SmsSqlAdapter;
import com.example.NotificationService.constants.SmsStatus;
import com.example.NotificationService.entity.sql.SmsSql;
import com.example.NotificationService.kafka.SmsProducer;
import com.example.NotificationService.models.requests.SmsRequestModel;
import com.example.NotificationService.models.response.SmsResponseModel;
import com.example.NotificationService.repository.SmsSqlRepository;
import com.example.NotificationService.services.SmsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@Service
@Validated
public class SmsServiceImpl implements SmsService {

    private final SmsProducer kafkaProducer;
    private final SmsSqlRepository smsSqlRepository;

    @Autowired
    SmsServiceImpl(SmsProducer kafkaProducer, SmsSqlRepository smsSqlRepository) {
        this.kafkaProducer = kafkaProducer;
        this.smsSqlRepository = smsSqlRepository;
    }

    @Override
    public SmsResponseModel sendSms(@Valid @RequestBody SmsRequestModel smsRequestModel) {
        SmsSql smsSql = SmsSqlAdapter.createSmsSql(smsRequestModel.getPhoneNumber(), smsRequestModel.getMessage());
        smsSql.setStatus(SmsStatus.INIT);
        SmsSql savedSmsSql = smsSqlRepository.save(smsSql);

        kafkaProducer.sendMessage(savedSmsSql.getId(), 2);

        return SmsSendResponseBodyAdapter.createResponseBody(
                savedSmsSql.getId(),
                "Successfully Sent"
        );
    }

    @Override
    public SmsSql findById(Long id) {
        Optional<SmsSql> smsSqlOptional = smsSqlRepository.findById(id);
        if(smsSqlOptional.isPresent()) {
            return smsSqlOptional.get();
        }
        return null;
    }

    @Override
    public void updateMessage(SmsSql consumedMessage) throws IllegalArgumentException{
        if(consumedMessage == null) {
            throw new IllegalArgumentException("Null message body");
        }
        smsSqlRepository.save(consumedMessage);
    }

}

