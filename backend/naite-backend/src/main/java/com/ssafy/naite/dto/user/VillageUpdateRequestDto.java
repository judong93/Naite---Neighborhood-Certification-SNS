package com.ssafy.naite.dto.user;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VillageUpdateRequestDto {
    @ApiParam(value = "유저 기본주소", required = true)
    private String userBasicAddress;

    @ApiParam(value = "유저 상세주소", required = true)
    private String userDetailAddress;

    @ApiParam(value = "유저 법정동", required = true)
    private String userDong;

    @Builder
    public VillageUpdateRequestDto(String userBasicAddress, String userDetailAddress, String userDong) {
        this.userBasicAddress = userBasicAddress;
        this.userDetailAddress = userDetailAddress;
        this.userDong = userDong;
    }
}
