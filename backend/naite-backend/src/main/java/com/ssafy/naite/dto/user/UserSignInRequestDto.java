package com.ssafy.naite.dto.user;

import com.ssafy.naite.domain.user.User;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignInRequestDto {

    @ApiParam(value = "유저 아이디", required = true)
    private String userId;
    @ApiParam(value = "유저 비밀번호", required = true)
    private String userPw;

    @Builder
    public UserSignInRequestDto(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }

    @Override
    public String
    toString() {
        return "UserSignInRequestDto{" +
                "userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                '}';
    }

    public User toEntity(){
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .build();
    }
}
