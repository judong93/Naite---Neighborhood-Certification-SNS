package com.ssafy.naite.service.chat;

import com.ssafy.naite.domain.chat.ChatMessage;
import com.ssafy.naite.domain.chat.ChatMessageRepository;
import com.ssafy.naite.domain.chat.ChatRoom;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.chat.ChatMessageGetDto;
import com.ssafy.naite.dto.chat.ChatMessagePostDto;
import com.ssafy.naite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    public ChatMessageGetDto save(ChatMessagePostDto dto, int userNo) {
       ChatMessage chatMessage =  chatMessageRepository.save(ChatMessage.builder()
        .message(dto.getMessage())
        .time(LocalDateTime.now())
        .chatRoom(new ChatRoom(dto.getRoomNo()))
        .user(new User(userNo))
        .build());

        // 메세지 시간 정보
        String messageTime = "";
        if (chatMessage.getTime().plusHours(1).isAfter(LocalDateTime.now())) {
            messageTime = "방금 전";
        }
        else if (chatMessage.getTime().plusDays(1).isAfter(LocalDateTime.now())) {
            int subHour = LocalDateTime.now().getHour() - chatMessage.getTime().getHour();
            if(subHour < 0) subHour += 24;
            messageTime = subHour + "시간 전";
        }
        else {
            messageTime = chatMessage.getTime().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)")).toString();
        }

       return ChatMessageGetDto.builder()
               .roomNo(chatMessage.getChatRoom().getRoomNo())
               .messageNo(chatMessage.getMessageNo())
               .message(chatMessage.getMessage())
               .time(messageTime)
               .userNick(userService.findByUserNo(userNo).getUserNick())
               .userPic(userService.findByUserNo(userNo).getUserPic())
               .userOwn(true)
               .build();
    }

    public List<ChatMessage> getMessages(Integer roomNo) {
        return chatMessageRepository.findByChatRoom(new ChatRoom(roomNo));
    }
}
