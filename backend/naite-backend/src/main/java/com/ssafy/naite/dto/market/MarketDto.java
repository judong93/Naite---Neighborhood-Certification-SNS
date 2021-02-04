package com.ssafy.naite.dto.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.market.Market;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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

        public MarketResponseDto(Market market) {
            this.marketNo = market.getMarketNo();
            this.board = market.getBoard();
            this.smallCategoryNo = market.getSmallCategoryNo();
            this.marketCost = market.getMarketCost();
            this.marketIsCompleted = market.getMarketIsCompleted();
        }
    }
}
