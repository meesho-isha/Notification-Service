package com.example.NotificationService.services;

import java.util.List;

public interface BlacklistService {

    void addToBlacklist(List<String> phoneNumbers);

    void removeFromBlacklist(List<String> phoneNumbers);

    List<String> getBlacklist();

    boolean isBlacklisted(String phoneNumber);

}

