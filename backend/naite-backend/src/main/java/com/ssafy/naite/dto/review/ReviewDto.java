package com.ssafy.naite.dto.review;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReviewDto {

    /**
     * insert review dto
     */
    @Getter
    @NoArgsConstructor
    public static class ReviewSaveRequestDto {
        private Board board;
        private int reviewStar;
        private int smallCategoryNo;

        @Builder
        public ReviewSaveRequestDto(Board board, int reviewStar, int smallCategoryNo) {
            this.board = board;
            this.reviewStar = reviewStar;
            this.smallCategoryNo = smallCategoryNo;
        }

        public Review toEntity(int userNo) {
            this.board.setUserNo(userNo);
            return Review.builder()
                    .board(board)
                    .reviewStar(reviewStar)
                    .smallCategoryNo(smallCategoryNo)
                    .build();
        }
    }

    /**
     * update review dto
     */
    @Getter
    @NoArgsConstructor
    public static class ReviewUpdateRequestDto {
        private Board board;
        private int reviewStar;
        private int smallCategoryNo;

        @Builder
        public ReviewUpdateRequestDto(Board board, int reviewStar, int smallCategoryNo) {
            this.board = board;
            this.reviewStar = reviewStar;
            this.smallCategoryNo = smallCategoryNo;
        }
    }

    /**
     * select review dto
     */
    @Getter
    @Setter
    public static class ReviewResponseDto {
        private int reviewNo;
        private Board board;
        private int reviewStar;
        private int smallCategoryNo;
        private List<String> usersWithLike = new ArrayList<String>();
        private String userNick;
        private String boardCreatedAt;
        private int boardCommentCnt;

        public ReviewResponseDto(Review review) {
            this.reviewNo = review.getReviewNo();
            this.board = review.getBoard();
            this.reviewStar = review.getReviewStar();
            this.smallCategoryNo = review.getSmallCategoryNo();
            if(this.board.getBoardCreatedAt().plusHours(1).isAfter(LocalDateTime.now())) {
                this.boardCreatedAt = "방금 전";
            } else if (this.board.getBoardCreatedAt().plusDays(1).isAfter(LocalDateTime.now())) {
                int subHour = LocalDateTime.now().getHour() - this.board.getBoardCreatedAt().getHour();
                if(subHour < 0) subHour += 24;
                this.boardCreatedAt = subHour + "시간 전";
            } else {
                this.boardCreatedAt = this.board.getBoardCreatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)"));
            }
        }
    }
}
