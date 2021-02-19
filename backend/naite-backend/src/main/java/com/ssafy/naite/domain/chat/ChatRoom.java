package com.ssafy.naite.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomNo;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> messages = new ArrayList<>();

    private Integer userOne;
    private Integer userTwo;
    private LocalDateTime updatedAt;

    public ChatRoom () {}

    @Builder
    public ChatRoom (Integer userOne, Integer userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
        this.updatedAt = LocalDateTime.now();
    }

    public ChatRoom(Integer roomNo) {
        this.roomNo = roomNo;
    }

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .userOne(userOne)
                .userTwo(userTwo)
        .build();
    }

    public void updateTime() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomNo=" + roomNo +
                ", messages=" + messages +
                '}';
    }
}