package com.ssafy.naite.dto.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.market.Market;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class MarketDto {

    /**
     * insert market dto
     */
    @Getter
    @NoArgsConstructor
    public static class MarketSaveRequestDto {
        private Board board;
        private int smallCategoryNo;
        private int marketCost;

        @Builder
        public MarketSaveRequestDto(Board board, int smallCategoryNo, int marketCost, String marketPlace, int marketPerson, String time) {
            this.board = board;
            this.smallCategoryNo = smallCategoryNo;
            this.marketCost = marketCost;
        }

        public Market toEntity(int userNo) {
            this.board.setUserNo(userNo);
            return Market.builder()
                    .board(board)
                    .smallCategoryNo(smallCategoryNo)
                    .marketCost(marketCost)
                    .build();
        }
    }

    /**
     * update market dto
     */
    @Getter
    @NoArgsConstructor
    public static class MarketUpdateRequestDto {
        private Board board;
        private int smallCategoryNo;
        private int marketCost;
        private int marketIsCompleted;

        @Builder
        public MarketUpdateRequestDto(Board board, int smallCategoryNo, int marketCost, String marketPlace, int marketIsCompleted, int marketPerson, String time) {
            this.board = board;
            this.smallCategoryNo = smallCategoryNo;
            this.marketCost = marketCost;
            this.marketIsCompleted = marketIsCompleted;
        }
    }

    /**
     * select market dto
     */
    @Getter
    @Setter
    public static class MarketResponseDto {
        private int marketNo;
        private Board board;
        private int smallCategoryNo;
        private int marketCost;
        private int marketIsCompleted;
        private List<String> usersWithLike = new ArrayList<String>();
        private String userNick;
        private String boardCreatedAt;

        public MarketResponseDto(Market market) {
            this.marketNo = market.getMarketNo();
            this.board = market.getBoard();
            this.smallCategoryNo = market.getSmallCategoryNo();
            this.marketCost = market.getMarketCost();
            this.marketIsCompleted = market.getMarketIsCompleted();
            if(this.board.getBoardCreatedAt().plusHours(1).isAfter(LocalDateTime.now())) {
                this.boardCreatedAt = "방금 전";
            } else if (this.board.getBoardCreatedAt().plusDays(1).isAfter(LocalDateTime.now())) {
                this.boardCreatedAt = (LocalDateTime.now().getHour() - this.board.getBoardCreatedAt().getHour()) + "시간 전";
            } else {
                this.boardCreatedAt = this.board.getBoardCreatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)"));
            }
        }
    }
}
