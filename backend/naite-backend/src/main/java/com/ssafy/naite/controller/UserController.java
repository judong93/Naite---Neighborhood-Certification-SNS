package com.ssafy.naite.controller;

import com.ssafy.naite.domain.user.User;
//import com.ssafy.naite.dto.user.AuthKeySaveRequestDto;
import com.ssafy.naite.dto.user.AuthKeySaveRequestDto;
import com.ssafy.naite.dto.user.EmailSendRequestDto;
import com.ssafy.naite.dto.user.UserSignUpRequestDto;
//import com.ssafy.naite.service.user.AuthKeyService;
import com.ssafy.naite.dto.util.Response;

import com.ssafy.naite.service.user.AuthKeyService;
import com.ssafy.naite.service.user.EmailService;
import com.ssafy.naite.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Random;


//@RequiredArgsConstructor
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final AuthKeyService authKeyService;
    private final EmailService emailService;

    @PostMapping("/signup")
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

    @PostMapping("/email/send")
    @ApiOperation(value = "이메일 인증 코드 전송")
    public Response emailSend(@RequestBody EmailSendRequestDto emailSendRequestDto) throws MessagingException {
        // 회원가입 시 이메일 인증일 경우
//        if (emailSendRequestDto.getType() == 0) {

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
                            "		아래 <b style=\"color: #02b875\">'메일 인증'</b> 버튼을 클릭하여 회원가입을 완료해 주세요.<br />"																													+
                            "		감사합니다."																																															+
                            "	</p>"																																																	+
                            "	<a style=\"color: #FFF; text-decoration: none; text-align: center;\""																																	+
                            "	href=\"http://localhost:8080/email/auth?email=" + user.getUserEmail() + "&key=" + key + "\" target=\"_blank\">"														+

                            "		<p"																																																	+
                            "			style=\"display: inline-block; width: 210px; height: 45px; margin: 30px 5px 40px; background: #02b875; line-height: 45px; vertical-align: middle; font-size: 16px;\">"							+
                            "			메일 인증</p>"																																														+
                            "	</a>"																																																	+
                            "	<div style=\"border-top: 1px solid #DDD; padding: 5px;\"></div>"																																		+
                            " </div>"
            );
            emailcontent.append("</body>");
            emailcontent.append("</html>");
            emailService.sendMail("kimmk0924@gmail.com", "[나이테] 회원가입 이메일 인증", emailcontent.toString());

            return new Response("success", "이메일 인증 코드 전송 완료", null);
        }

        else {
            // 없으면 존재하지 않는 회원이라고 에러 보냄
            return new Response("error", "존재하지 않는 회원입니다.", null);
        }
//        }
        // TODO: 아이디 찾기 시 이메일 인증 일 경우 (type = 1)
//        else if (emailSendRequestDto.getType() == 1) {
//
//        }
//        else {
//
//        }
    }

    @GetMapping("/email/auth")
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
