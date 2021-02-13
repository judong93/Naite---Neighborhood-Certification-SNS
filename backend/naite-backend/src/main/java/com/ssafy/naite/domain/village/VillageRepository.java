package com.ssafy.naite.domain.village;

import com.ssafy.naite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VillageRepository extends JpaRepository<Village, VillagePK> {
    Optional<Village> findByUserNo(int userNo);
}
