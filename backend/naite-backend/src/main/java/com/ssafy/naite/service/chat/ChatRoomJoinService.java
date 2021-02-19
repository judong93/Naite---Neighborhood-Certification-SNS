package com.ssafy.naite.service.chat;

import com.ssafy.naite.domain.chat.ChatRoom;
import com.ssafy.naite.domain.chat.ChatRoomJoin;
import com.ssafy.naite.domain.chat.ChatRoomJoinRepository;
import com.ssafy.naite.domain.chat.ChatRoomRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinJpaRepository;
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository; // chatRoomService에서 접근하려했지만 순환참조문제 생김

    // 해당 사용자가 속해있는 채팅방 넘버 반환
    public List<Integer> getRoomIdsByUserNo(Integer userNo) {
        List<ChatRoomJoin> list = chatRoomJoinJpaRepository.findByUser(new User(userNo));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).getChatRoom().getRoomNo());
        }
        return result;
    }

    // ChatRoomJoin에 사용자 추가
    public void insertNewRoom(Integer userNo, Integer roomNo) {
        chatRoomJoinJpaRepository.save(ChatRoomJoin.builder()
                                        .user(userService.findByUserNo(userNo))
                                        .chatRoom(chatRoomRepository.findById(roomNo).get())
                                        .build());
    }

    // 특정 사용자가 해당 채팅방에 속해있는지 체크
    public boolean checkValidUser(Integer userNo, Integer roomNo) {
        Optional<ChatRoomJoin> chatRoomJoin = chatRoomJoinJpaRepository.findByUserAndChatRoom(new User(userNo), new ChatRoom(roomNo));
        if (chatRoomJoin.isPresent()) return true;
        else return false;
    }

}
