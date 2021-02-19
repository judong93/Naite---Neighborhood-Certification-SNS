package com.ssafy.naite.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Entity Class로 Database를 접근하게 해줄 JpaRepository
// Dao 역할 (DB Layer 접근자)
// 인터페이스 생성 후 JpaRepository<Entity 클래스, PK타입> 상속 -> CRUD 메소드 자동 생성
public interface UserRepository extends JpaRepository<User, Integer> {
    // null에 의한 오류를 최소화 하기 위해 리턴으로 Optional<T>를 받는다.
    Optional<User> findByUserEmail(String userEmail);
    Optional<User> findByUserId(String userId);
    Optional<User> findByUserNick(String userNick);
}
