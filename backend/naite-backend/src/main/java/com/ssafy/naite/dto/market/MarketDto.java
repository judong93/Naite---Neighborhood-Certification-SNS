package com.ssafy.naite.dto.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.market.Market;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
        private String marketPlace;
        private int marketPerson;
        private String time;

        @Builder
        public MarketSaveRequestDto(Board board, int smallCategoryNo, int marketCost, String marketPlace, int marketPerson, String time) {
            this.board = board;
            this.smallCategoryNo = smallCategoryNo;
            this.marketCost = marketCost;
            this.marketPlace = marketPlace;
            this.marketPerson = marketPerson;
            this.time = time;
        }

        public Market toEntity() {
            return Market.builder()
                    .board(board)
                    .smallCategoryNo(smallCategoryNo)
                    .marketCost(marketCost)
                    .marketPlace(marketPlace)
                    .marketPerson(marketPerson)
                    .time(time)
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
        private String marketPlace;
        private int marketIsCompleted;
        private int marketPerson;
        private String time;

        @Builder
        public MarketUpdateRequestDto(Board board, int smallCategoryNo, int marketCost, String marketPlace, int marketIsCompleted, int marketPerson, String time) {
            this.board = board;
            this.smallCategoryNo = smallCategoryNo;
            this.marketCost = marketCost;
            this.marketPlace = marketPlace;
            this.marketIsCompleted = marketIsCompleted;
            this.marketPerson = marketPerson;
            this.time = time;
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
        private String marketPlace;
        private int marketIsCompleted;
        private int marketPerson;
        private LocalDateTime time;

        public MarketResponseDto(Market market) {
            this.marketNo = market.getMarketNo();
            this.board = market.getBoard();
            this.smallCategoryNo = market.getSmallCategoryNo();
            this.marketCost = market.getMarketCost();
            this.marketPlace = market.getMarketPlace();
            this.marketIsCompleted = market.getMarketIsCompleted();
            this.marketPerson = market.getMarketPerson();
            this.time = market.getMarketEndTime();
            if (time.isBefore(LocalDateTime.now())) {
                this.marketIsCompleted = 1;
            }
        }
    }
}
