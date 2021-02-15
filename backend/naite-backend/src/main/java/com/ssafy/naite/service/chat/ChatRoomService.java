package com.ssafy.naite.service.chat;

import com.ssafy.naite.domain.chat.ChatMessage;
import com.ssafy.naite.domain.chat.ChatRoom;
import com.ssafy.naite.domain.chat.ChatRoomRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.chat.ChatMessageGetDto;
import com.ssafy.naite.dto.chat.ChatRoomGetDto;
import com.ssafy.naite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomJoinService chatRoomJoinService;
    private final ChatMessageService chatMessageService;
    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * 채팅방 개설
     */
    public ChatRoom makeNewRoom(Integer userNo, String otherNick) {

        Optional<User> other = userRepository.findByUserNick(otherNick);
        Integer otherNo = other.get().getUserNo();

        Optional<ChatRoom> chatRoom = chatRoomRepository.checkExisted(userNo, otherNo);
        if (chatRoom.isPresent()) {
            return chatRoom.get();
        }

        ChatRoom newRoom = chatRoomRepository.save(ChatRoom.builder()
                    .userOne(userNo)
                    .userTwo(otherNo)
                    .build());
        chatRoomJoinService.insertNewRoom(newRoom.getUserOne(), newRoom.getRoomNo());
        chatRoomJoinService.insertNewRoom(newRoom.getUserTwo(), newRoom.getRoomNo());
        return newRoom;
    }

    /**
     * 채팅방 목록 조회
     */
    public List<ChatRoomGetDto> getRooms(Integer userNo) {
        List<Integer> roomIds = chatRoomJoinService.getRoomIdsByUserNo(userNo);
        List<Integer> sortedRoomIds = chatRoomRepository.getSortedRoomIds(roomIds);
        List<ChatRoomGetDto> result = new ArrayList<>();

        for (int i = 0; i < sortedRoomIds.size(); i++) {
            int roomId = sortedRoomIds.get(i);
            int otherUserNo = 0; // 상대방 유저 넘버

            Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomId);
            if (chatRoom.isPresent()) {
                if (chatRoom.get().getUserOne() == userNo) {
                    otherUserNo = chatRoom.get().getUserTwo();
                } else {
                    otherUserNo = chatRoom.get().getUserOne();
                }
            }

            // 상대방 유저 정보
            User other = userService.findByUserNo(otherUserNo);

            // 마지막 메세지 정보
            ChatMessage lastMessage = getLastMessage(roomId);
            String lastMessageTime = "";
            if (lastMessage == null) {
                result.add(new ChatRoomGetDto(roomId, other.getUserNick(), other.getUserPic(), "", ""));
            } else {
                if (lastMessage.getTime().plusHours(1).isAfter(LocalDateTime.now())) {
                    lastMessageTime = "방금 전";
                }
                else if (lastMessage.getTime().plusDays(1).isAfter(LocalDateTime.now())) {
                    int subHour = LocalDateTime.now().getHour() - lastMessage.getTime().getHour();
                    if(subHour < 0) subHour += 24;
                    lastMessageTime = subHour + "시간 전";
                }
                else {
                    lastMessageTime = lastMessage.getTime().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)")).toString();
                }

                result.add(new ChatRoomGetDto(roomId, other.getUserNick(), other.getUserPic(), lastMessage.getMessage(), lastMessageTime));
            }
        }
        return result;
    }

    /**
     * 채팅방 상세 조회 (메세지 조회)
     */
    public List<ChatMessageGetDto> getMessages(Integer roomNo, Integer userNo) {
        List<ChatMessage> messages = chatMessageService.getMessages(roomNo);
        List<ChatMessageGetDto> result = new ArrayList<>();
        int otherUserNo = 0;
        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(roomNo);
        if (chatRoom.isPresent()) {
            if (chatRoom.get().getUserOne() == userNo) {
                otherUserNo = chatRoom.get().getUserTwo();
            } else {
                otherUserNo = chatRoom.get().getUserOne();
            }
        }
        String myNick = userService.findByUserNo(userNo).getUserNick();
        String otherNick = userService.findByUserNo(otherUserNo).getUserNick();
        for (int i = 0; i < messages.size(); i++) {
            ChatMessage chatMessage = messages.get(i);

            // 유저 정보
            boolean userOwn = false;
            String userNick = otherNick;
            if (chatMessage.getUser().getUserNo() == userNo) {
                userOwn = true;
                userNick = myNick;
            }
            String userPic = userService.findByUserNo(chatMessage.getUser().getUserNo()).getUserPic();

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

            result.add(ChatMessageGetDto.builder()
                    .roomNo(roomNo)
                    .messageNo(chatMessage.getMessageNo())
                    .message(chatMessage.getMessage())
                    .time(messageTime)
                    .userNick(userNick)
                    .userPic(userPic)
                    .userOwn(userOwn)
                    .build());
        }
        return result;
    }

    /**
     * 마지막 메세지 가져오기
     */
    public ChatMessage getLastMessage(Integer roomNo) {
        List<ChatMessage> messages = chatMessageService.getMessages(roomNo);
        if (messages.size() == 0) return null;
        else return messages.get(messages.size()-1);
    }

}
