package com.ssafy.naite.domain.market;

import com.ssafy.naite.domain.board.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "market")
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int marketNo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board_no")
    private Board board;

    @Column(nullable = false)
    private int smallCategoryNo;

    @Column(nullable = false)
    private int marketCost;

    @Column(nullable = false)
    private int marketIsCompleted;

    private String marketPlace;

    private int marketPerson;

    private LocalDateTime marketEndTime;

    @Builder
    public Market(int marketNo, Board board, int smallCategoryNo, int marketCost, int marketIsCompleted, String marketPlace, int marketPerson, String time) {
        this.marketNo = marketNo;
        this.board = board;
        board.setBoardCreatedAt(LocalDateTime.now());
        board.setBoardUpdatedAt(LocalDateTime.now());
        this.smallCategoryNo = smallCategoryNo;
        this.marketCost = marketCost;
        this.marketIsCompleted = marketIsCompleted;
        this.marketPlace = marketPlace;
        this.marketPerson = marketPerson;
        this.marketEndTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    public void update(int smallCategoryNo, int marketCost, int marketIsCompleted, String marketPlace, int marketPerson, String time) {
        this.smallCategoryNo = smallCategoryNo;
        this.marketCost = marketCost;
        this.marketIsCompleted = marketIsCompleted;
        this.marketPlace = marketPlace;
        this.marketPerson = marketPerson;
        this.marketEndTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    public void marketClose() {
        this.marketIsCompleted = 1;
    }
}
