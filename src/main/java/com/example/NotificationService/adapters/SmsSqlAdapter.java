package com.example.NotificationService.adapters;

import java.time.LocalDateTime;
import com.example.NotificationService.entity.sql.SmsSql;
import org.springframework.stereotype.Component;

@Component
public class SmsSqlAdapter {
    public static SmsSql createSmsSql(String phoneNumber, String message) {

        return SmsSql.builder()
                .phoneNumber(phoneNumber)
                .message(message)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

}
