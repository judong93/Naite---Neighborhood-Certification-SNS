package com.ssafy.naite.dto.user;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfilePwUpdateRequestDto {

    @ApiParam(value = "현재 비밀번호")
    private String currentPw;
    @ApiParam(value = "새 비밀번호")
    private String newPw;

    public ProfilePwUpdateRequestDto(String currentPw, String newPw) {
        this.currentPw = currentPw;
        this.newPw = newPw;
    }
}
