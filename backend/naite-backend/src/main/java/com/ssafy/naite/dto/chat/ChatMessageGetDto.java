package com.ssafy.naite.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessageGetDto {
    public Integer roomNo;
    public Integer messageNo;
    public String message;
    public String time;
    public String userNick;
    public String userPic;
    public boolean userOwn;

    @Builder
    public ChatMessageGetDto(Integer roomNo, Integer messageNo, String message, String time, String userNick, String userPic, boolean userOwn) {
        this.roomNo = roomNo;
        this.messageNo = messageNo;
        this.message = message;
        this.time = time;
        this.userNick = userNick;
        this.userPic = userPic;
        this.userOwn = userOwn;
    }
}
