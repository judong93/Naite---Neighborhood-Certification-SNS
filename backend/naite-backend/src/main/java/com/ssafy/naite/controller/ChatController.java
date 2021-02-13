package com.ssafy.naite.controller;

import com.ssafy.naite.dto.chat.ChatMessageGetDto;
import com.ssafy.naite.dto.chat.ChatMessagePostDto;
import com.ssafy.naite.service.chat.ChatMessageService;
import com.ssafy.naite.service.user.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageService chatMessageService;
    private final JwtService jwtService;

    @MessageMapping("/chat/message")
    public void message(ChatMessageGetDto message) {
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomNo(), message);
    }

    @PostMapping("/chat/message")
    @ResponseBody
    public ChatMessageGetDto insertMessage(@RequestBody ChatMessagePostDto chatMessagePostDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        return chatMessageService.save(chatMessagePostDto, userNo);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }

}