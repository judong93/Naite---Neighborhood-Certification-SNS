package com.ssafy.naite.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessagePostDto {
    public Integer roomNo;
    public String message;

    @Builder
    public ChatMessagePostDto(Integer roomNo, String message) {
        this.roomNo = roomNo;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChatMessagePostDto{" +
                "roomNo=" + roomNo +
                ", message='" + message + '\'' +
                '}';
    }
}
