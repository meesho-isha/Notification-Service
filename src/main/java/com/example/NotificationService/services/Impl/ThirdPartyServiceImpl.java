package com.example.NotificationService.services.Impl;

import com.example.NotificationService.adapters.ThirdPartyRequestBodyAdapter;
import com.example.NotificationService.entity.sql.SmsSql;
import com.example.NotificationService.kafka.SmsProducer;
import com.example.NotificationService.models.requests.ThirdPartyRequestModel;
import com.example.NotificationService.models.response.ThirdPartyResponseModel;
import com.example.NotificationService.constants.SmsStatus;
import com.example.NotificationService.constants.CustomStatusCode;
import com.example.NotificationService.services.SmsService;
import com.example.NotificationService.services.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    private final SmsService smsService;
    private final SmsProducer smsProducer;
    private final RestTemplate restTemplate;

    @Value("${third.party.url}")
    private String thirdPartyUrl;
    @Value("${third.party.api.key}")
    private String thirdPartyApiKey;

    @Autowired
    public ThirdPartyServiceImpl(SmsService smsService, SmsProducer smsProducer, RestTemplate restTemplate) {
        this.smsService = smsService;
        this.smsProducer = smsProducer;
        this.restTemplate = restTemplate;
    }

    HttpHeaders getHttpHeaders(String key) {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.setContentType(MediaType.APPLICATION_JSON);
        httpHeader.add("key", key);
        return httpHeader;
    }

    @Override
    public void sendSms(SmsSql consumedSms, int retryCount) throws Exception {
        ThirdPartyRequestModel requestModel = ThirdPartyRequestBodyAdapter.convertSqlToRequestBody(consumedSms);
        HttpHeaders httpHeaders = getHttpHeaders(thirdPartyApiKey);

        try {
            ResponseEntity<ThirdPartyResponseModel> responseEntity = restTemplate.exchange(
                    thirdPartyUrl,
                    HttpMethod.POST,
                    new HttpEntity<>(requestModel, httpHeaders),
                    ThirdPartyResponseModel.class
            );

            ThirdPartyResponseModel responseBody = responseEntity.getBody();

            if (responseBody != null) {
                handleResponseBody(responseBody, consumedSms, retryCount);
            }
            else {
                throw new Exception("Error sending the message");
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            log.info("HTTP error: {} with message: {}", e.getStatusCode(),e.getMessage());
        } catch (ResourceAccessException e) {
            log.info("Connection/read timeout: {}", e.getMessage());
        }
    }

    void handleResponseBody(ThirdPartyResponseModel responseBody, SmsSql consumedMessage, int retryCount) {
        String responseCode = responseBody.getResponse().getFirst().getCode();
        if (responseCode.equals(CustomStatusCode.API_SUCCESS_CODE)) {
            consumedMessage.setStatus(SmsStatus.SENT);
            log.info("Sent message to third-party API: {}", SmsStatus.SENT);
            smsService.updateMessage(consumedMessage);
        } else {
            handleFailure(consumedMessage, responseCode, responseBody.getResponse().get(0).getDescription(), retryCount);
        }
    }

    void handleFailure(SmsSql message, String failureCode, String failureComments, int retryCount) {
        message.setStatus(SmsStatus.FAILED);
        message.setFailureCode(failureCode);
        message.setFailureComments(failureComments);

        smsService.updateMessage(message);

        retrySend(message,retryCount);
    }

    void retrySend(SmsSql message, int retryCount) {
        if(retryCount>0) {
            log.info("Retrying sending message {} with retry count: {}", message.getId(), retryCount);
            smsProducer.sendMessage(message.getId(), retryCount-1);
        }
    }

}
