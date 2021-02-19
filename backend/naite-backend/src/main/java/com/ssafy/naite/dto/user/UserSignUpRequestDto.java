package com.ssafy.naite.dto.user;

import com.ssafy.naite.domain.user.User;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserSignUpRequestDto {

    @ApiParam(value = "유저 아이디", required = true)
    private String userId;

    @ApiParam(value = "유저 이름", required = true)
    private String userName;

    @ApiParam(value = "유저 이메일", required = true)
    private String userEmail;

    @ApiParam(value = "유저 비밀번호", required = true)
    private String userPw;

    @ApiParam(value = "유저 기본주소", required = true)
    private String userBasicAddress;

    @ApiParam(value = "유저 상세주소", required = true)
    private String userDetailAddress;

    @ApiParam(value = "유저 법정동", required = true)
    private String userDong;

    @ApiParam(value = "유저 닉네임", required = true)
    private String userNick;

    @ApiParam(value = "유저 프로필 사진", required = false)
    private String userPic;

    @ApiParam(value = "기본", required = false)
    private MultipartFile files;

    @Builder
    public UserSignUpRequestDto(String userId, String userName, String userEmail, String userPw, String userBasicAddress, String userDetailAddress, String userDong, String userNick, String userPic) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userBasicAddress = userBasicAddress;
        this.userDetailAddress = userDetailAddress;
        this.userDong = userDong;
        this.userNick = userNick;
        this.userPic = userPic;
    }

    @Override
    public String toString() {
        return "UserSignUpRequestDto{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPw='" + userPw + '\'' +
                ", userBasicAddress='" + userBasicAddress + '\'' +
                ", userDetailAddress='" + userDetailAddress + '\'' +
                ", userDong='" + userDong + '\'' +
                ", userNick='" + userNick + '\'' +
                ", userPic='" + userPic + '\'' +
                '}';
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userName(userName)
                .userEmail(userEmail)
                .userPw(userPw)
                .userBasicAddress(userBasicAddress)
                .userDetailAddress(userDetailAddress)
                .userNick(userNick)
                .userPic(userPic)
                .build();
    }

}
