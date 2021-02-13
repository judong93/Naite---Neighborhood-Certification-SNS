package com.ssafy.naite.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    public ChatRoom () {}

    @Builder
    public ChatRoom (Integer userOne, Integer userTwo) {
        this.userOne = userOne;
        this.userTwo = userTwo;
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

    @Override
    public String toString() {
        return "ChatRoom{" +
                "roomNo=" + roomNo +
                ", messages=" + messages +
                '}';
    }
}