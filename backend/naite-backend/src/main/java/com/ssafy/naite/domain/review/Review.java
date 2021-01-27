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
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="review_no")
    private int review_no;
    private int small_category_no;
//    private int board_no; //겹치는 부분 제외
    private int review_star;

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
    public Review(Board board, int review_star, int small_category_no) {
        this.board = board;
        board.setBoard_created_at(LocalDateTime.now());
        board.setBoard_updated_at(LocalDateTime.now());
        this.review_star = review_star;
        this.small_category_no = small_category_no;
    }

    public void update(int review_star, int small_category_no) {
        this.review_star = review_star;
        this.small_category_no = small_category_no;
    }
}
