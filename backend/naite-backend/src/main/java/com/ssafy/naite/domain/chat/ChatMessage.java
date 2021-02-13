package com.ssafy.naite.domain.chat;

import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer messageNo;

    private String message;

    private LocalDateTime time;

    @ManyToOne
    @JoinColumn(name = "room_no")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public ChatMessage(String message, LocalDateTime time, ChatRoom chatRoom, User user) {
        this.message = message;
        this.time = time;
        this.chatRoom = chatRoom;
        this.user = user;
    }
}
