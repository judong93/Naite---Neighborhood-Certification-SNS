package com.ssafy.naite.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthKeyRepository extends JpaRepository<AuthKey, Integer> {
    Optional<AuthKey> findByUserEmail(String userEmail);
    Optional<AuthKey> findByUserEmailAndAuthType(String userEmail, Integer authType);
}
