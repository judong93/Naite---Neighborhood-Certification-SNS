package com.ssafy.naite.domain.picture;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "picture")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int picNo;

    @Column(nullable = false)
    private int boardNo;

    @Column(nullable = false)
    private String boardPic;

    @Builder
    public Picture(int picNo, int boardNo, String boardPic) {
        this.picNo = picNo;
        this.boardNo = boardNo;
        this.boardPic = boardPic;
    }
}
