package com.ssafy.naite.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.domain.village.VillageRepository;

import com.ssafy.naite.dto.user.*;
import com.ssafy.naite.service.board.BoardService;
import com.ssafy.naite.service.market.MarketService;
import com.ssafy.naite.service.util.Salt;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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

            String rootPath = "/home/ubuntu/images/";
            String apiPath = "https://i4a402.p.ssafy.io/images/";
            String changeName = "basic_profile.png";
            if(requestDto.getFiles() != null) {
                MultipartFile files = requestDto.getFiles();
                String originalName = files.getOriginalFilename();
                changeName = requestDto.getUserNick() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS")) + "_" + originalName;
                String filePath = rootPath + changeName;
                File dest = new File(filePath);
                files.transferTo(dest);
            }

            User user = requestDto.toEntity();
            user.setUserPw(encodedPw);
            user.setUserSalt(salt);
            user.setUserPic(apiPath + changeName);

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
        if (existed.isPresent()) return existed.get();
        else return null;
    }

    /**
     * userNo로 user 찾기
     */
    public User findByUserNo(int userNo) {
        return userRepository.findById(userNo).get();
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
                .userScore(user.getUserScore())
                .commentCnt(commentCnt)
                .boardCnt(boardCnt)
                .dealCnt(dealCnt)
                .userPic(user.getUserPic())
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

    /** 비밀번호 찾기에서 비밀번호 변경 */
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

    /** 프로필에서 비밀번호 변경 */
    @Transactional
    public int updateUserProfilePw(int userNo, ProfilePwUpdateRequestDto requestDto) throws Exception {
        User user = findByUserNo(userNo);

        String salt = user.getUserSalt();
        String password = saltUtil.encodePassword(salt, requestDto.getCurrentPw());
        // 비밀번호 일치여부 확인
        if (!user.getUserPw().equals(password)) throw new Exception("현재 비밀번호가 일치하지 않습니다.");
        else {
            String newSalt = BCrypt.gensalt();
            String encodedPw = BCrypt.hashpw(requestDto.getNewPw(), newSalt);

            user.setUserPw(encodedPw);
            user.setUserSalt(newSalt);
        }
        return user.getUserNo();
    }

    /** 회원 주소 변경 */
    @Transactional
    public int updateUserAddress(int userNo, VillageUpdateRequestDto requestDto) {
        User user = userRepository.findById(userNo).orElseThrow(()-> new IllegalAccessError("해당 유저가 존재하지 않습니다."));
        user.updateAddress(requestDto.getUserBasicAddress(),requestDto.getUserDetailAddress());
        userRepository.save(user);
        return userNo;
    }

    /** 회원 탈퇴 */
    @Transactional
    public int leaveUser(int userNo) {
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalAccessError("해당 유저가 존재하지 않습니다."));
        user.leave((byte) 0);
        userRepository.save(user);
        return userNo;
    }

    /** 회원 복귀 */
    @Transactional
    public int restoreUser(int userNo) {
        User user = userRepository.findById(userNo).orElseThrow(() -> new IllegalAccessError("해당 유저가 존재하지 않습니다."));
        user.leave((byte) 1);
        userRepository.save(user);
        return userNo;
    }

    /**
     * 유저 번호로 유저 프로필 조회
     */
    @Transactional
    public UserGetProfileResponseDto getProfileByNo(int userNo) throws Exception {
        User user = userRepository.findById(userNo).get();
        int commentCnt = commentRepository.findByUser(user).size(); // 댓글 수
        int boardCnt = boardService.findAllBoardsByUserNo(user.getUserNo()).size(); // 게시글 수
        int dealCnt = marketService.getMarketByUser(user.getUserNo()); // 장터거래 수

        return UserGetProfileResponseDto.builder()
                .userName(user.getUserName())
                .userNick(user.getUserNick())
                .userScore(user.getUserScore())
                .commentCnt(commentCnt)
                .boardCnt(boardCnt)
                .dealCnt(dealCnt)
                .userPic(user.getUserPic())
                .build();
    }

    /**
     * 닉네임 변경
     */
    public void updateUserNick(int userNo, String newNick) throws Exception{
        if (checkDuplicateNick(newNick)) {
            throw new Exception("이미 사용 중인 닉네임입니다.");
        }
        Optional<User> user = userRepository.findById(userNo);

        if (user.isPresent()) {
            user.get().updateNick(newNick);
            userRepository.save(user.get());
        } else {
            throw new Exception("존재하지 않는 사용자입니다.");
        }
    }

    /**
     * 유저 프로필 변경
     */
    @Transactional
    public int updateUserPic(MultipartFile files, int userNo) throws IOException {
        User user = userRepository.findById(userNo).get();
        String rootPath = "/home/ubuntu/images/";
        String apiPath = "https://i4a402.p.ssafy.io/images/";
        String changeName = "basic_profile.png";
        if (files != null && !files.getOriginalFilename().equals("")) {
            String originalName = files.getOriginalFilename();
            changeName = user.getUserNick() + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS")) + "_" + originalName;
            String filePath = rootPath + changeName;
            File dest = new File(filePath);
            files.transferTo(dest);
        }
        user.updateUserPic(apiPath + changeName);
        return userRepository.save(user).getUserNo();
    }
}
