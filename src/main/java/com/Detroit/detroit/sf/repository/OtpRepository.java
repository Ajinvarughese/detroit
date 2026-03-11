package com.Detroit.detroit.sf.repository;

import com.Detroit.detroit.sf.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {
    void deleteByCreatedAtBefore(LocalDateTime time);
    Optional<OTP> findByEmail(String email);
}
