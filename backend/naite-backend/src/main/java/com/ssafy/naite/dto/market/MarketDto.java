package com.ssafy.naite.dto.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.market.Market;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MarketDto {

    /**
     * insert market dto
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class MarketSaveRequestDto {
//        private Board board;
        private int bigCategoryNo;
        private String boardTitle;
        private String boardContent;
        private String boardPic;
        private int unknownFlag;
        private int openFlag;
        private int smallCategoryNo;
        private int marketCost;
        private List<MultipartFile> files;

        @Builder
        public MarketSaveRequestDto(int bigCategoryNo, String boardTitle, String boardContent, String boardPic, int unknownFlag, int openFlag, int smallCategoryNo, int marketCost) {
//            this.board = board;
            this.bigCategoryNo = bigCategoryNo;
            this.boardTitle = boardTitle;
            this.boardContent = boardContent;
            this.boardPic = boardPic;
            this.unknownFlag = unknownFlag;
            this.openFlag = openFlag;
            this.smallCategoryNo = smallCategoryNo;
            this.marketCost = marketCost;
        }

        public Market toEntity(Board board) {
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
        private String marketCost;
        private int marketIsCompleted;
        private List<String> usersWithLike = new ArrayList<String>();
        private String userNick;
        private String boardCreatedAt;
        private String boardCreatedAtSimple;
        private int boardCommentCnt;
        private List<String> files = new ArrayList<String>();
        private String userPic;

        public MarketResponseDto(Market market) {
            this.marketNo = market.getMarketNo();
            this.board = market.getBoard();
            this.smallCategoryNo = market.getSmallCategoryNo();
            this.marketCost = NumberFormat.getInstance().format(market.getMarketCost());
            this.marketIsCompleted = market.getMarketIsCompleted();
            if(this.board.getBoardCreatedAt().plusHours(1).isAfter(LocalDateTime.now())) {
                this.boardCreatedAt = this.boardCreatedAtSimple = "방금 전";
            } else if (this.board.getBoardCreatedAt().plusDays(1).isAfter(LocalDateTime.now())) {
                int subHour = LocalDateTime.now().getHour() - this.board.getBoardCreatedAt().getHour();
                if(subHour < 0) subHour += 24;
                this.boardCreatedAt = this.boardCreatedAtSimple = subHour + "시간 전";
            } else {
                this.boardCreatedAt = this.board.getBoardCreatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)"));
                this.boardCreatedAtSimple = this.board.getBoardCreatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        }
    }

    /**
     * select market by user dto
     */
    @Getter
    @Setter
    public static class MarketByUserResponseDto {
        private int marketNo;
        private int boardNo;
        private String boardTitle;
        private String boardContent;
        private int bigCategoryNo;
        private int marketIsCompleted;
        private int isSeller;
        private int evalIsCompleted;
        private List<String> files = new ArrayList<String>();


        @Builder
        public MarketByUserResponseDto(int marketNo, int boardNo, String boardTitle, String boardContent, int bigCategoryNo, int marketIsCompleted, int isSeller, int evalIsCompleted, List<String> files) {
            this.marketNo = marketNo;
            this.boardNo = boardNo;
            this.boardTitle = boardTitle;
            this.boardContent = boardContent;
            this.bigCategoryNo = bigCategoryNo;
            this.marketIsCompleted = marketIsCompleted;
            this.isSeller = isSeller;
            this.evalIsCompleted = evalIsCompleted;
            this.files = files;
        }
    }

    /**
     * update market complete dto
     */
    @Getter
    @Setter
    public static class MarketCompleteRequestDto {
        private String buyerNick;
        private int evalScore;
        private String evalComment;
    }

    /**
     * insert evaluation dto
     */
    @Getter
    @Setter
    public static class EvalSellerFromBuyerRequestDto {
        private int evalScore;
        private String evalComment;
    }

    /**
     * get evaluations dto
     */
    @Getter
    @Setter
    public static class EvalsResponseDto {
        private int sellerUserNo;
        private String sellerUserNick;
        private int sellerScore;
        private String sellerComment;
        private int buyerUserNo;
        private String buyerUserNick;
        private int buyerScore;
        private String buyerComment;

        @Builder
        public EvalsResponseDto(int sellerUserNo, String sellerUserNick, int sellerScore, String sellerComment, int buyerUserNo, String buyerUserNick, int buyerScore, String buyerComment) {
            this.sellerUserNo = sellerUserNo;
            this.sellerUserNick = sellerUserNick;
            this.sellerScore = sellerScore;
            this.sellerComment = sellerComment;
            this.buyerUserNo = buyerUserNo;
            this.buyerUserNick = buyerUserNick;
            this.buyerScore = buyerScore;
            this.buyerComment = buyerComment;
        }
    }
}
