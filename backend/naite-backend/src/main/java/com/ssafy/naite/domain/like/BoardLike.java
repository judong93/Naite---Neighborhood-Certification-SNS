package com.ssafy.naite.domain.like;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@IdClass(LikePK.class)
@Table(name = "board_like")
public class BoardLike {

    @Id
    private int userNo;

    @Id
    private int boardNo;

    @Builder
    public BoardLike(int userNo, int boardNo) {
        this.userNo = userNo;
        this.boardNo = boardNo;
    }
}
