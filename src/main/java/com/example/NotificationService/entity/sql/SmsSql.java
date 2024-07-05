package com.example.NotificationService.entity.sql;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sms_requests")
public class SmsSql {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="phone_number", nullable = false)
    private String phoneNumber;

    @Column(name="message", nullable = false)
    private String message;

    @Column(name="status")
    private String status;

    @Column(name="failure_code")
    private String failureCode;

    @Column(name="failure_comments")
    private String failureComments;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    private void update() {
        this.updatedAt = LocalDateTime.now();
    }

}
