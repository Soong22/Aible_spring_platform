package com.aivle.platform.repository;

import com.aivle.platform.domain.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByEmail(String email);
    void deleteAllByEmail(String email);

    boolean existsByEmail(String email);
}
