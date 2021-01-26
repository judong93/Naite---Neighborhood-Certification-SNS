package com.ssafy.naite.dto.user;

import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpRequestDto {

    private String userId;
    private String userName;
    private String userEmail;
    private String userPw;
    private String userBasicAddress;
    private String userDetailAddress;
    private String userNick;
    private String userPic;

    @Builder
    public UserSignUpRequestDto(String userId, String userName, String userEmail, String userPw, String userBasicAddress, String userDetailAddress, String userNick, String userPic) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userBasicAddress = userBasicAddress;
        this.userDetailAddress = userDetailAddress;
        this.userNick = userNick;
        this.userPic = userPic;
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
