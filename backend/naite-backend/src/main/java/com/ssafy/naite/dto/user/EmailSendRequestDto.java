package com.ssafy.naite.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailSendRequestDto {
    private Integer type;
    private String userEmail;
    private String userId;

    @Builder
    public EmailSendRequestDto(Integer type, String userEmail, String userId) {
        this.type = type;
        this.userEmail = userEmail;
        this.userId = userId;
    }
}
