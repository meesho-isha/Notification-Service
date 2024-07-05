package com.example.NotificationService.controllers;

import com.example.NotificationService.models.common.DataResponse;
import com.example.NotificationService.models.requests.PhoneNumbersRequestModel;
import com.example.NotificationService.services.BlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Validated
public class BlacklistController {

    private final BlacklistService blacklistService;

    @Autowired
    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @PostMapping("/blacklist")
    public ResponseEntity<DataResponse>addToBlacklist(@Valid @RequestBody PhoneNumbersRequestModel requestBody) {
        blacklistService.addToBlacklist(requestBody.getPhoneNumbers());
        return ResponseEntity.ok(new DataResponse("Successfully blacklisted"));
    }

    @DeleteMapping("/blacklist")
    public ResponseEntity<DataResponse>removeFromBlacklist(@Valid @RequestBody PhoneNumbersRequestModel requestBody) {
        blacklistService.removeFromBlacklist(requestBody.getPhoneNumbers());
        return ResponseEntity.ok(new DataResponse("Successfully whitelisted"));
    }

    @GetMapping("/blacklist")
    public ResponseEntity<DataResponse> getBlacklist() {
        List<String> blacklist = blacklistService.getBlacklist();
        return ResponseEntity.ok(new DataResponse(blacklist));
    }

}
