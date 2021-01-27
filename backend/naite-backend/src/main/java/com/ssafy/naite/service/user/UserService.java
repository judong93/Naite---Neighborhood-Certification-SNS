package com.ssafy.naite.service.user;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.user.UserSignUpRequestDto;
<<<<<<< HEAD:backend/naite-backend/src/main/java/com/ssafy/naite/service/user/UserService.java
import com.ssafy.naite.service.util.Salt;
import lombok.AllArgsConstructor;
=======
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
>>>>>>> f5c810b5faad367f07caf898d520b2e95ea7b732:backend/naite-backend/src/main/java/com/ssafy/naite/service/User/UserService.java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
//@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final Salt saltService;

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    @Transactional
    public User save(UserSignUpRequestDto requestDto) throws Exception{
        Optional<User> existed = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (!existed.isPresent()) {
            // 패스워드 암호화
            String salt = BCrypt.gensalt();
            String encodedPw = BCrypt.hashpw(requestDto.getUserPw(), salt);

            User user = requestDto.toEntity();
            user.setUserPw(encodedPw);
            user.setUserSalt(salt);

            // 회원가입 진행
            return userRepository.save(user);
        } else {
            throw new Exception("이미 가입된 회원입니다.");
        }
    }

    /**
     * 존재하는 회원인지 확인 (Email 중복인지 체크)
     */
    @Transactional
    public User findByEmail(String email) {
        Optional<User> existed = userRepository.findByUserEmail(email);
        return existed.get();
    }

}
