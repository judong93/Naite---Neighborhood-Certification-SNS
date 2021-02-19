package com.ssafy.naite.domain.review;

import com.ssafy.naite.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_no")
    private int reviewNo;

    @Column(nullable = false)
    private int smallCategoryNo;

    @Column(nullable = false)
    private int reviewStar;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_no")
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    @Builder
    public Review(Board board, int reviewStar, int smallCategoryNo) {
        this.board = board;
        board.setBoardCreatedAt(LocalDateTime.now());
        board.setBoardUpdatedAt(LocalDateTime.now());
        this.reviewStar = reviewStar;
        this.smallCategoryNo = smallCategoryNo;
    }

    public void update(int reviewStar, int smallCategoryNo) {
        this.reviewStar = reviewStar;
        this.smallCategoryNo = smallCategoryNo;
    }
}
