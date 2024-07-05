package com.example.NotificationService.services.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.NotificationService.models.common.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandlerService {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        FieldError fieldErrorPhone = ex.getBindingResult().getFieldError("phoneNumber");
        FieldError fieldErrorSms = ex.getBindingResult().getFieldError("message");
        if(fieldErrorPhone != null) {
            ErrorResponse errorResponse = new ErrorResponse("INVALID_REQUEST", "phone_number is mandatory");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        if(fieldErrorSms != null) {
            ErrorResponse errorResponse = new ErrorResponse("INVALID_REQUEST", "message is mandatory");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        ErrorResponse errorResponse = new ErrorResponse("INVALID_REQUEST", "Validation error");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
