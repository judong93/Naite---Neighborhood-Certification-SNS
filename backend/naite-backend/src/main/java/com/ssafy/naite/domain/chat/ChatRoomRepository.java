package com.ssafy.naite.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    @Query("select c from ChatRoom c where (c.userOne = :userNo and c.userTwo = :otherNo) or ((c.userOne = :otherNo and c.userTwo = :userNo))")
    Optional<ChatRoom> checkExisted(Integer userNo, Integer otherNo);

    @Query("select c.roomNo from ChatRoom c where c.roomNo in (:ids) order by c.updatedAt desc")
    List<Integer> getSortedRoomIds(List<Integer> ids);
}
