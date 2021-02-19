package com.ssafy.naite.dto.user;


import com.ssafy.naite.domain.user.AuthKey;
import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthKeySaveRequestDto {
//    private Integer userNo;
    private String userEmail;
    private String authKey;
    private Integer authType;

    @Builder
    public AuthKeySaveRequestDto(String userEmail, String authKey, Integer authType) {
        this.userEmail = userEmail;
        this.authKey = authKey;
        this.authType = authType;
    }

    public AuthKey toEntity() {
        return AuthKey.builder()
                .userEmail(userEmail)
                .authKey(authKey)
                .authType(authType)
                .build();
    }
}
