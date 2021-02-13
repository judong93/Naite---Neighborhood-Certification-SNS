package com.ssafy.naite.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatRoomGetDto {
    public Integer roomNo;
    public String otherNick;
    public String otherPic;
    public String lastMessage;
    public String lastMessageTime;

    public ChatRoomGetDto(Integer roomNo, String otherNick, String otherPic, String lastMessage, String lastMessageTime) {
        this.roomNo = roomNo;
        this.otherNick = otherNick;
        this.otherPic = otherPic;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }
}
