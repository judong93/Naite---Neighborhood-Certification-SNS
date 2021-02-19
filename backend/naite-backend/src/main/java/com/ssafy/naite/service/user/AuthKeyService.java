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
    public void save(AuthKeySaveRequestDto authKeySaveRequestDto) throws Exception{
        Optional<AuthKey> existed = authKeyRepository.findByUserEmail(authKeySaveRequestDto.getUserEmail());
        if (existed.isPresent()) {
            AuthKey authKey = existed.get();

            // 회원가입 도중 이메일 인증 미완료된 가입되지 않은 사용자가 비밀번호 찾기를 하려고 할 때
            if (authKeySaveRequestDto.getAuthType() == 1 && authKey.getAuthType() == 0 && !authKey.getAuthKey().equals("success"))
                throw new Exception("회원가입 이메일 인증 미완료된 사용자입니다.");

            authKey.updateKey(authKeySaveRequestDto.getAuthKey());
            authKey.updateType(authKeySaveRequestDto.getAuthType());
            authKeyRepository.save(authKey);
        } else {
            authKeyRepository.save(authKeySaveRequestDto.toEntity());
        }
    }

    public void compare(String userEmail, String certified, Integer keyType) throws Exception{
        Optional<AuthKey> authKey = authKeyRepository.findByUserEmailAndAuthType(userEmail,keyType);
        if (!authKey.isPresent())
            throw new Exception("제대로 가입되지 않은 사용자");
        if (certified.equals(authKey.get().getAuthKey())) {
            AuthKey realKey = authKey.get();
            realKey.updateKey("success");
            authKeyRepository.save(realKey);
        } else {
            throw new Exception("인증 키가 일치하지 않습니다.");
        }
    }
}
