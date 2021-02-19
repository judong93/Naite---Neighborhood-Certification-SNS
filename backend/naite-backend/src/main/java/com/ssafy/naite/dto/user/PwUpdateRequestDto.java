package com.ssafy.naite.dto.user;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PwUpdateRequestDto {
    @ApiParam(value = "유저 비밀번호")
    private String userPw;

    public PwUpdateRequestDto(String userPw) {
        this.userPw = userPw;
    }
}
