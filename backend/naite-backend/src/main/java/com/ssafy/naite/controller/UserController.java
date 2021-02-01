package com.ssafy.naite.controller;

import com.ssafy.naite.domain.user.User;
//import com.ssafy.naite.dto.user.AuthKeySaveRequestDto;
import com.ssafy.naite.dto.user.AuthKeySaveRequestDto;
import com.ssafy.naite.dto.user.EmailSendRequestDto;
import com.ssafy.naite.dto.user.UserSignInRequestDto;
import com.ssafy.naite.dto.user.UserSignUpRequestDto;
//import com.ssafy.naite.service.user.AuthKeyService;
import com.ssafy.naite.dto.util.Response;

import com.ssafy.naite.service.user.AuthKeyService;
import com.ssafy.naite.service.user.EmailService;
import com.ssafy.naite.service.user.JwtService;
import com.ssafy.naite.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private final EmailService emailService;
    private final JwtService jwtService;

    @PostMapping("/sign/signin")
    @ApiOperation(value = "회원 로그인")
    public ResponseEntity<Map<String,Object>> signin(@RequestBody UserSignInRequestDto userSignInRequestDto, HttpServletResponse res){
        Map<String,Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            User loginUser = userService.signin(userSignInRequestDto);
            if(loginUser!=null) {
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
        return new ResponseEntity<Map<String,Object>>(resultMap,status);
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
    public Response save(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        try {
            User user = userService.save(userSignUpRequestDto);
            // 이메일 인증 키 auth_key에 "empty"로 저장 -> 미인증 상태
            authKeyService.save(new AuthKeySaveRequestDto(user, "empty", 0));
            return new Response("success", "회원가입 완료", null);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    @PostMapping("/sign/email/send")
    @ApiOperation(value = "이메일 인증 코드 전송")
    public Response emailSend(@RequestBody EmailSendRequestDto emailSendRequestDto) throws MessagingException {

        // user에 해당 user_email 있는지 확인
        User user = userService.findByEmail(emailSendRequestDto.getUserEmail());
        if (user != null) {
            // 랜덤키 생성
            Random random = new Random();
            StringBuffer buffer = new StringBuffer();
            int num = 0;
            int size = 6; // 인증 키 자릿수

            while(buffer.length() < size) {
                num = random.nextInt(10);
                buffer.append(num);
            }
            String key = buffer.toString();

            // auth_key 테이블 업데이트
            try {
                authKeyService.update(user.getUserEmail(), key);
            } catch (Exception e) {
                return new Response("error",e.getMessage(),null);
            }

            // 이메일 전송
            StringBuffer emailcontent = new StringBuffer();
            emailcontent.append("<!DOCTYPE html>");
            emailcontent.append("<html>");
            emailcontent.append("<head>");
            emailcontent.append("</head>");
            emailcontent.append("<body>");
            emailcontent.append(
                    " <div" 																																																	+
                            "	style=\"font-family: 'Apple SD Gothic Neo', 'sans-serif' !important; width: 400px; height: 600px; border-top: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;\">"		+
                            "	<h1 style=\"margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;\">"																															+
                            "		<span style=\"font-size: 15px; margin: 0 0 10px 3px;\">NAITE</span><br />"																													+
                            "		<span style=\"color: #02b875\">메일인증</span> 안내입니다."																																				+
                            "	</h1>\n"																																																+
                            "	<p style=\"font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;\">"																													+
                            user.getUserName()																																																+
                            "		님 안녕하세요.<br />"																																													+
                            "		나이테에 가입해 주셔서 진심으로 감사드립니다.<br />"																																						+
                            "		아래의 인증 번호를 가입 페이지에서 입력해주세요.<br />"																													+
                            "		감사합니다."																																															+
                            "	</p>"
                            + "<b style=\"font-size: 20px; color: #02b875\"> 인증번호 : "+key+"</b>" +
                            "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
                            " </div>"
            );
            emailcontent.append("</body>");
            emailcontent.append("</html>");
            emailService.sendMail(user.getUserEmail(), "[나이테] 회원가입 이메일 인증", emailcontent.toString());

            return new Response("success", "이메일 인증 코드 전송 완료", null);
        }
        else {
            // 없으면 존재하지 않는 회원이라고 에러 보냄
            return new Response("error", "존재하지 않는 회원입니다.", null);
        }

    }

    @GetMapping("/sign/email/auth")
    @ApiOperation(value = "이메일 인증")
    public Response update(@RequestParam("email") String userEmail, @RequestParam("key") String certified) throws Exception{
        // auth_key에 있는 값과 비교
        try {
            authKeyService.compare(userEmail, certified);
            return new Response("success", "이메일 인증 완료", null);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

}
