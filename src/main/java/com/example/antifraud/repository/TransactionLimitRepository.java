package com.example.antifraud.repository;

import com.example.antifraud.repository.entity.TransactionLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionLimitRepository extends JpaRepository<TransactionLimit, Long> {
    TransactionLimit findByNumber(String number);
}
