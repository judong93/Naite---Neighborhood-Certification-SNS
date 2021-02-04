package com.ssafy.naite.service.user;

import com.ssafy.naite.domain.user.AuthKey;
import com.ssafy.naite.domain.user.AuthKeyRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.user.AuthKeySaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class AuthKeyService {
    private final AuthKeyRepository authKeyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void save(AuthKeySaveRequestDto authKeySaveRequestDto) {
        AuthKey existed = authKeyRepository.findByUserEmail(authKeySaveRequestDto.getUserEmail()).get();
        if (existed != null) {
            existed.updateKey(authKeySaveRequestDto.getAuthKey());
            authKeyRepository.save(existed);
        } else {
            authKeyRepository.save(authKeySaveRequestDto.toEntity());
        }
    }

    public void compare(String userEmail, String certified, Integer keyType) throws Exception{
        AuthKey authKey = authKeyRepository.findByUserEmailAndAuthType(userEmail,keyType).get();
        if (certified.equals(authKey.getAuthKey())) {
            authKey.updateKey("success");
            authKeyRepository.save(authKey);
        } else {
            throw new Exception("인증 키가 일치하지 않습니다.");
        }
    }
}
