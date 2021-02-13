package com.ssafy.naite.service.chat;

import com.ssafy.naite.domain.chat.ChatRoomJoin;
import com.ssafy.naite.domain.chat.ChatRoomJoinRepository;
import com.ssafy.naite.domain.chat.ChatRoomRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ChatRoomJoinService {
    private final ChatRoomJoinRepository chatRoomJoinJpaRepository;
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository; // chatRoomService에서 접근하려했지만 순환참조문제 생김

    public List<Integer> getRoomIdsByUserNo(Integer userNo) {
        List<ChatRoomJoin> list = chatRoomJoinJpaRepository.findByUser(new User(userNo));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            result.add(list.get(i).getChatRoom().getRoomNo());
        }
        return result;
    }

    public void insertNewRoom(Integer userNo, Integer roomNo) {
        chatRoomJoinJpaRepository.save(ChatRoomJoin.builder()
                                        .user(userService.findByUserNo(userNo))
                                        .chatRoom(chatRoomRepository.findById(roomNo).get())
                                        .build());
    }

}
