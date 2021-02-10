package com.ssafy.naite.controller;

import com.ssafy.naite.domain.user.User;
//import com.ssafy.naite.dto.user.AuthKeySaveRequestDto;
import com.ssafy.naite.dto.user.*;
//import com.ssafy.naite.service.user.AuthKeyService;
import com.ssafy.naite.dto.util.Response;

import com.ssafy.naite.service.market.MarketService;
import com.ssafy.naite.service.user.AuthKeyService;
import com.ssafy.naite.service.user.EmailService;
import com.ssafy.naite.service.user.JwtService;
import com.ssafy.naite.service.user.UserService;
import com.ssafy.naite.service.village.VillageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


//@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "회원 관리")
@RestController
public class UserController {
    private final UserService userService;
    private final AuthKeyService authKeyService;
    private final MarketService marketService;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final VillageService villageService;

    @PostMapping("/sign/signin")
    @ApiOperation(value = "회원 로그인")
    public ResponseEntity<Map<String, Object>> signin(@RequestBody UserSignInRequestDto userSignInRequestDto, HttpServletResponse res) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            User loginUser = userService.signin(userSignInRequestDto);
            if (loginUser != null) {
                // 토큰 생성
                String token = jwtService.create(loginUser);
                System.out.println(token);
                resultMap.put("auth-token", token);
                resultMap.put("user-id", loginUser.getUserId());
                resultMap.put("user-name", loginUser.getUserName());
                status = HttpStatus.ACCEPTED;
            } else {
                resultMap.put("message", "로그인 실패");
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원 정보 조회")
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUser(HttpServletRequest req){
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        System.out.println(">>>>>> " + jwtService.get(req.getHeader("auth-token")));
        try {
            resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
            status = HttpStatus.ACCEPTED;
        } catch (RuntimeException e) {
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }


    @PostMapping("/sign/signup")
    @ApiOperation(value = "회원가입")
    public Response save(@ModelAttribute UserSignUpRequestDto userSignUpRequestDto) {
        try {
            User user = userService.save(userSignUpRequestDto);
            // 이메일 인증 키 auth_key에 "empty"로 저장 -> 미인증 상태
            authKeyService.save(new AuthKeySaveRequestDto(user.getUserEmail(), "empty", 0));
            villageService.saveVillage(user, userSignUpRequestDto.getUserDong());
            return new Response("success", "회원가입 완료", null);
        } catch (Exception e) {
            if (e.getMessage().equals("DuplicatedAll")) return new Response("error", "모두 중복", null);
            else if (e.getMessage().equals("DuplicatedEmail")) return new Response("error", "이메일 중복", null);
            else return new Response("error", "아이디 중복", null);
        }
    }

    @PostMapping("/sign/email/send")
    @ApiOperation(value = "이메일 인증 코드 전송")
    public Response emailSend(@RequestBody EmailSendRequestDto emailSendRequestDto) throws MessagingException {

        // user에 해당 user_email 있는지 확인
        User user = userService.findByEmail(emailSendRequestDto.getUserEmail());
        if (user == null) {

            // 랜덤키 생성
            Random random = new Random();
            StringBuffer buffer = new StringBuffer();
            int num = 0;
            int size = 6; // 인증 키 자릿수

            while (buffer.length() < size) {
                num = random.nextInt(10);
                buffer.append(num);
            }
            String key = buffer.toString();
            System.out.println("key " + key);
            authKeyService.save(new AuthKeySaveRequestDto(emailSendRequestDto.getUserEmail(), key, 0));

            // 이메일 전송
            StringBuffer emailcontent = new StringBuffer();
            emailcontent.append("<!DOCTYPE html>");
            emailcontent.append("<html>");
            emailcontent.append("<head>");
            emailcontent.append("</head>");
            emailcontent.append("<body>");
            emailcontent.append(
                    " <div" +
                            "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">" +
                            "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">" +
                            "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">NAITE</span><br />" +
                            "		<span style=\"color: #02b875\">메일인증</span> 안내입니다." +
                            "	</h1>\n" +
                            "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">" +
                            "		안녕하세요.<br />" +
                            "		아래의 인증 번호를 나이테 홈페이지에서 입력해주세요.<br />" +
                            "		감사합니다." +
                            "	</p>"
                            + "<b style=\"font-size: 20px; color: #02b875\"> 인증번호 : " + key + "</b>" +
                            "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>" +
                            " </div>"
            );
            emailcontent.append("</body>");
            emailcontent.append("</html>");
            emailService.sendMail(emailSendRequestDto.getUserEmail(), "[나이테] 이메일 인증", emailcontent.toString());

            return new Response("success", "이메일 인증 코드 전송 완료", null);
        } else {
            return new Response("error", "이미 가입된 회원입니다.", null);
        }

    }

    @GetMapping("/sign/email/auth")
    @ApiOperation(value = "이메일 인증")
    public Response update(@RequestParam("email") String userEmail, @RequestParam("key") String certified, @RequestParam("type") Integer keyType) throws Exception {
        // auth_key에 있는 값과 비교
        try {
            authKeyService.compare(userEmail, certified, keyType);
            return new Response("success", "이메일 인증 완료", null);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    @GetMapping("/profile")
    @ApiOperation(value = "프로필 조회")
    public Response getProfile(@ApiParam(value = "유저 토큰") @RequestHeader("auth-token") String userToken,
                               @ApiParam(value = "유저 아이디") @RequestHeader("userId") String userId) {
        try {
            UserGetProfileResponseDto dto = userService.getProfile(userId);
            return new Response("success", "프로필 조회 성공", dto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    @PostMapping("/sign/nick/{nick}")
    @ApiOperation(value = "닉네임 중복 체크")
    public Response checkDuplicateNick(@ApiParam(value = "닉네임") @PathVariable("nick") String userNick) throws Exception{
       if (userService.checkDuplicateNick(userNick)) {
           return new Response("error", "이미 존재하는 닉네임입니다.", null);
       } else {
           return new Response("success", "사용 가능한 닉네임 입니다.", null);
       }
    }

    @PostMapping("/sign/id/{id}")
    @ApiOperation(value = "아이디 중복 체크")
    public Response checkDuplicateId(@ApiParam(value = "아이디") @PathVariable("id") String userId) throws Exception{
        if (userService.checkDuplicateId(userId)) {
            return new Response("error", "이미 존재하는 아이디 입니다.", null);
        } else {
            return new Response("success", "사용 가능한 아이디 입니다.", null);
        }
    }

    @GetMapping("/sign/{userEmail}")
    @ApiOperation(value = "아이디 찾기", notes = "이메일을 조회하여 사용자 아이디를 조회합니다.")
    public ResponseEntity<String> findUserIdByEmail(@PathVariable String userEmail) throws Exception {
        try {
            User user = userService.findByEmail(userEmail);
            return new ResponseEntity<String>(user.getUserId(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("아이디 찾기 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "비밀번호 재설정", notes = "사용자의 인증 후 비밀번호를 재설정합니다.")
    @PutMapping("/sign/password/{userId}")
    public ResponseEntity<String> updatePassword(@PathVariable String userId,@RequestBody PwUpdateRequestDto pwUpdateRequestDto) {
        try {
            User user = userService.updateUserPw(userId, pwUpdateRequestDto);
            authKeyService.save(new AuthKeySaveRequestDto(user.getUserEmail(), "empty", 1));
            return new ResponseEntity<String>("비밀번호 재설정 완료", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("비밀번호 재설정 실패", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @ApiOperation(value = "토큰 유효성 검사")
    @GetMapping("/token")
    public Response checkToken(HttpServletRequest req) {
        try {
            jwtService.checkValid(req.getHeader("auth-token"));
            return new Response("success", "유효한 토큰입니다.", null);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    @ApiOperation(value = "회원 탈퇴")
    @PutMapping("/leave")
    public Response leaveUser(HttpServletRequest req) {
        try {
            int userNo = getUserNo(req);
            int leftUser = userService.leaveUser(userNo);
            return new Response("success","회원 탈퇴하셨습니다.", leftUser);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    @ApiOperation(value = "회원 복귀")
    @PutMapping("/restore")
    public Response restoreUser(HttpServletRequest req) {
        try {
            int userNo = getUserNo(req);
            int restoredUser = userService.restoreUser(userNo);
            return new Response("success","회원 복귀하셨습니다.", restoredUser);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
