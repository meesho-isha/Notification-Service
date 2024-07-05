package com.example.NotificationService.repository;

import com.example.NotificationService.entity.sql.BlacklistSql;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistSqlRepository extends JpaRepository<BlacklistSql, String> {
    List<BlacklistSql> findByPhoneNumberIn(List<String> phoneNumbers);
}
