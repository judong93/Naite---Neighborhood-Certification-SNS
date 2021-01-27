package com.ssafy.naite.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmailSendRequestDto {
    private Integer type;
    private String userEmail;

    @Builder
    public EmailSendRequestDto(Integer type, String userEmail) {
        this.type = type;
        this.userEmail = userEmail;
    }
}
