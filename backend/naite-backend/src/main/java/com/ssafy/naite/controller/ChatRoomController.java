package com.ssafy.naite.controller;

import com.ssafy.naite.domain.chat.ChatRoom;
import com.ssafy.naite.dto.chat.ChatMessageGetDto;
import com.ssafy.naite.dto.chat.ChatRoomGetDto;
import com.ssafy.naite.dto.util.Response;
import com.ssafy.naite.service.chat.ChatRoomJoinService;
import com.ssafy.naite.service.chat.ChatRoomService;
import com.ssafy.naite.service.user.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final ChatRoomJoinService chatRoomJoinService;
    private final JwtService jwtService;

    // 채팅 리스트 화면
    // 서버에서 테스트 할 화면
    // @GetMapping("/rooms")
    // @ApiIgnore
    // public String rooms(Model model) {
    //    return "/chat/room";
    // }

    // 모든 채팅방 목록 반환
    @GetMapping("/room")
    @ResponseBody
    public List<ChatRoomGetDto> room(HttpServletRequest req) {
        int userNo = getUserNo(req);
        return chatRoomService.getRooms(userNo);
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public Integer createRoom(@RequestParam("otherNick") String otherNick, HttpServletRequest req) {
        int userNo = getUserNo(req);
        ChatRoom chatRoom = chatRoomService.makeNewRoom(userNo, otherNick);
        return chatRoom.getRoomNo();
    }

    // 채팅방 입장 화면
    // 서버에서 테스트 할 화면
    @GetMapping("/room/enter/{roomId}")
    @ApiIgnore
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        return "/chat/roomdetail";
    }

    // 특정 채팅방 조회 (메세지 불러오기)
    @GetMapping("/room/{roomNo}")
    @ResponseBody
    public Response roomInfo(@PathVariable Integer roomNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        if (!chatRoomJoinService.checkValidUser(userNo, roomNo)) {
            return new Response("error", "해당 채팅방에 속하지 않은 사용자입니다.", null);
        }
        List<ChatMessageGetDto> result = chatRoomService.getMessages(roomNo, userNo);
        return new Response("success", "채팅방 메세지 조회 성공", result);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}