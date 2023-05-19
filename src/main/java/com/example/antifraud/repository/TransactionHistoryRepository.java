package com.example.antifraud.repository;

import com.example.antifraud.repository.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {

    List<TransactionHistory> findByDateIsGreaterThanEqual(LocalDateTime date);

    List<TransactionHistory> findByNumber(String number);
}
