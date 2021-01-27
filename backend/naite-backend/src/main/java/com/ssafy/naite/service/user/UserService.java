package com.ssafy.naite.service.user;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.user.UserSignUpRequestDto;
import com.ssafy.naite.service.util.Salt;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@RequiredArgsConstructor
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final Salt saltService;

    /**
     * 회원가입
     * @param requestDto
     * @return
     */
    @Transactional
    public void save(UserSignUpRequestDto requestDto) throws Exception{
        User user = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (user == null) {
            System.out.println("here 유저 없음 ");
            // 패스워드 암호화
//            String salt = saltService.genSalt();
//            String pw = saltService.encodePassword(requestDto.getUserPw(), salt);
//            user.setUserPw(pw);
//            user.setUserSalt(salt);

            // 회원가입 진행
            userRepository.save(user);
            userRepository.save(requestDto.toEntity());
        } else {
            System.out.println("here 이미 가입된 유저 ");
            throw new Exception("이미 가입된 회원입니다.");
        }
    }

//    /**
//     * 중복회원 검사
//     */
//    private boolean validateDuplicateUser(String userEmail) {
//
//
//        System.out.println("EMAIL: " + userEmail);
//        User existed = userRepository.findByUserEmail(userEmail);
//        System.out.println("EXIST : " + existed);
//        if (existed == null) return true;
//        else return false;
//    }

//    @Transactional
//    public Long update (Long id, PostsUpdateRequestDto requestDto) {
//        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
//        posts.update(requestDto.getTitle(), requestDto.getContent());
//        return id;
//    }
//
//    public PostsResponseDto findById(Long id) {
//        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
//        return new PostsResponseDto(entity);
//    }
}
