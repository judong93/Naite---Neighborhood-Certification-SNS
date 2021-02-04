package com.ssafy.naite.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.domain.village.VillageRepository;

import com.ssafy.naite.dto.user.PwUpdateRequestDto;
import com.ssafy.naite.dto.user.UserGetProfileResponseDto;
import com.ssafy.naite.dto.user.UserSignInRequestDto;
import com.ssafy.naite.dto.user.UserSignUpRequestDto;
import com.ssafy.naite.service.board.BoardService;
import com.ssafy.naite.service.market.MarketService;
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
    private final CommentRepository commentRepository;
    private final VillageRepository villageRepository;
    private final Salt saltUtil;
    private final JwtService jwtService;
    private final BoardService boardService;
    private final MarketService marketService;

    /** 
     * 로그인 
     */
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

    /** 
     * 회원가입 
     */
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

    /**
     * 유저 아이디로 User 찾기
     */
    public User findByUserId(String id) {
        return userRepository.findByUserId(id).get();
    }

    /**
     * 유저 프로필 조회
     */
    @Transactional
    public UserGetProfileResponseDto getProfile(String userId) throws Exception{
        User user = userRepository.findByUserId(userId).get();
        int commentCnt = commentRepository.findByUser(new User(user.getUserNo())).size(); // 댓글 수
        int boardCnt = boardService.findAllBoardsByUserNo(user.getUserNo()).size(); // 게시글 수
        int dealCnt = marketService.getMarketByUser(user.getUserNo()); // 장터거래 수

        return UserGetProfileResponseDto.builder()
                .userName(user.getUserName())
                .userNick(user.getUserNick())
                .commentCnt(commentCnt)
                .boardCnt(boardCnt)
                .dealCnt(dealCnt)
                .build();
    }

    /**
     * 닉네임 중복 체크
     */
    public boolean checkDuplicateNick(String userNick){
        Optional<User> existed = userRepository.findByUserNick(userNick);
        if (existed.isPresent()) return true;
        else return false;
    }

    /**
     * 아이디 중복 체크
     */
    public boolean checkDuplicateId(String userId){
        Optional<User> existed = userRepository.findByUserId(userId);
        if (existed.isPresent()) return true;
        else return false;
    }

    /** 비밀번호 변경 */
    @Transactional
    public User updateUserPw(String id, PwUpdateRequestDto requestDto) throws Exception {
        Optional<User> existed = userRepository.findByUserId(id);

        if (!existed.isPresent()) throw new Exception("등록된 아이디가 없습니다.");
        else {
            User user = existed.get();
            String salt = BCrypt.gensalt();
            String encodedPw = BCrypt.hashpw(requestDto.getUserPw(), salt);

            user.setUserPw(encodedPw);
            user.setUserSalt(salt);
            return user;
        }
    }

}
