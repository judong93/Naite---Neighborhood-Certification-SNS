package com.ssafy.naite.service.village;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.domain.village.Village;
import com.ssafy.naite.domain.village.VillagePK;
import com.ssafy.naite.domain.village.VillageRepository;
import com.ssafy.naite.dto.user.VillageUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class VillageService {
    private final UserRepository userRepository;
    private final VillageRepository villageRepository;

    public void saveVillage(User user, String dong) {
        villageRepository.save(Village.builder()
        .userNo(user.getUserNo())
        .villageName(dong)
        .build());
    }

    /** 회원 동 변경 */
    @Transactional
    public int updateVillage(int userNo, String dong){
        Village village = villageRepository.findByUserNo(userNo).orElseThrow(()-> new IllegalAccessError("해당 유저가 존재하지 않습니다."));
        village.updateVillage(dong);
        villageRepository.save(village);
        return userNo;
    }
}
