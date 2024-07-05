package com.example.NotificationService.repository;

import com.example.NotificationService.entity.sql.SmsSql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SmsSqlRepository extends JpaRepository<SmsSql, Long> { }