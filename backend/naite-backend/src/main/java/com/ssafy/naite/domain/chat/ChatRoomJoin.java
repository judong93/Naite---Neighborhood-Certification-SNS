package com.ssafy.naite.domain.chat;

import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoomJoin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer joinNo;

    @ManyToOne
    @JoinColumn(name =  "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_no")
    private ChatRoom chatRoom;

    @Builder
    public ChatRoomJoin(User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }
}