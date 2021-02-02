package com.ssafy.naite.service.user;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.user.UserSignInRequestDto;
import com.ssafy.naite.dto.user.UserSignUpRequestDto;
import com.ssafy.naite.service.util.Salt;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private  final Salt saltUtil;

    /** 로그인 */
    public User signin(UserSignInRequestDto requestDto) throws  Exception{
        Optional<User> existed = userRepository.findByUserId(requestDto.getUserId());
        if(!existed.isPresent()) throw new Exception("등록된 아이디가 없습니다.");
        else {
            User user = existed.get();
            // salt 해독
            String salt = user.getUserSalt();
            String password = saltUtil.encodePassword(salt, requestDto.getUserPw());
            // 비밀번호 일치여부 확인
            if (!user.getUserPw().equals(password)) throw new Exception("비밀번호가 일치하지 않습니다.");
            return user;
        }
    }

    /** 회원가입 */
    @Transactional
    public User save(UserSignUpRequestDto requestDto) throws Exception{
        Optional<User> existedId = userRepository.findByUserId(requestDto.getUserId());
        Optional<User> existedEmail = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (!existedEmail.isPresent() && !existedId.isPresent()) {
            // 패스워드 암호화
            String salt = BCrypt.gensalt();
            String encodedPw = BCrypt.hashpw(requestDto.getUserPw(), salt);

            User user = requestDto.toEntity();
            user.setUserPw(encodedPw);
            user.setUserSalt(salt);

            // 회원가입 진행
            return userRepository.save(user);
        } else {
            if (existedEmail.isPresent() && existedId.isPresent()) throw new Exception("DuplicatedAll");
            else if (existedEmail.isPresent()) throw new Exception("DuplicatedEmail");
            else throw new Exception("DuplicatedId");
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
