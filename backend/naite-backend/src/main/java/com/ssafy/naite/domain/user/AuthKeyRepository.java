package com.ssafy.naite.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthKeyRepository extends JpaRepository<AuthKey, Integer> {
    Optional<AuthKey> findByUser(User user);
    Optional<AuthKey> findByUserAndAuthType(User user, Integer authType);
}
