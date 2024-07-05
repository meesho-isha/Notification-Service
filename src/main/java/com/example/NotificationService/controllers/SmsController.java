package com.example.NotificationService.controllers;

import com.example.NotificationService.entity.sql.SmsSql;
import com.example.NotificationService.models.common.DataResponse;
import com.example.NotificationService.models.requests.SmsRequestModel;
import com.example.NotificationService.models.response.SmsResponseModel;
import com.example.NotificationService.services.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/sms")
@Validated
public class SmsController {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping("/send")
    public ResponseEntity<DataResponse> SendSms(@Valid @RequestBody SmsRequestModel smsRequestModel) {
        SmsResponseModel smsSendResponseBody = smsService.sendSms(smsRequestModel);
        return ResponseEntity.ok(new DataResponse(smsSendResponseBody));
    }

    @GetMapping("/{request_id}")
    public ResponseEntity<DataResponse> getDetailsById(@Valid @NumberFormat @PathVariable("request_id") Long requestId) {
        SmsSql requestedDetails = smsService.findById(requestId);
        return ResponseEntity.ok(new DataResponse(requestedDetails));
    }
}
