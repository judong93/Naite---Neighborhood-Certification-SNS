package com.ssafy.naite.service.village;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.village.Village;
import com.ssafy.naite.domain.village.VillagePK;
import com.ssafy.naite.domain.village.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VillageService {
    private final VillageRepository villageRepository;

    public void saveVillage(User user, String dong) {
        villageRepository.save(Village.builder()
        .userNo(user.getUserNo())
        .villageName(dong)
        .build());
    }
}
