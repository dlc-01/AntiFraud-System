package com.example.antifraud.repository;

import com.example.antifraud.repository.entity.SuspiciousIp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuspiciousIpRepository extends JpaRepository<SuspiciousIp, Long> {
    @Override
    Optional<SuspiciousIp> findById(Long id);

    boolean existsById(Long id);

    boolean existsByIp(String ip);

    void deleteByIp(String ip);
}
