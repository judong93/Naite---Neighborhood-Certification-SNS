package com.ssafy.naite.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomPostDto {
    public Integer from;
    public Integer to;

    public ChatRoomPostDto(Integer from, Integer to) {
        this.from = from;
        this.to = to;
    }
}
