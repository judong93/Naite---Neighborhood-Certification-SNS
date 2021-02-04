package com.ssafy.naite.domain.village;

import com.ssafy.naite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VillageRepository extends JpaRepository<Village, VillagePK> {
}
