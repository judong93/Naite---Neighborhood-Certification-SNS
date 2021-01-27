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
    private User user;
    private String authKey;
    private Integer authType;

    @Builder
    public AuthKeySaveRequestDto(User user, String authKey, Integer authType) {
        this.user = user;
        this.authKey = authKey;
        this.authType = authType;
    }

    public AuthKey toEntity() {
        return AuthKey.builder()
                .user(user)
                .authKey(authKey)
                .authType(authType)
                .build();
    }
}
