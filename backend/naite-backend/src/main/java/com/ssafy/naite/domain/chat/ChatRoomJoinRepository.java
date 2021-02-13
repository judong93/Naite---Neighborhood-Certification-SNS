package com.ssafy.naite.domain.chat;

import com.ssafy.naite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomJoinRepository extends JpaRepository<ChatRoomJoin, Integer> {
    List<ChatRoomJoin> findByUser(User user);
}
