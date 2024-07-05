package com.example.NotificationService.models.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private ErrorDetails error;

    public ErrorResponse(String code, String message) {
        this.error = new ErrorDetails(code, message);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetails {
        private String code;
        private String message;
    }

}
