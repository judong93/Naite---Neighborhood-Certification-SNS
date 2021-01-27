package com.ssafy.naite.dto.review;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.review.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReviewDto {

    /** insert review dto */
    @Getter
    @NoArgsConstructor
    public static class ReviewSaveRequestDto {
        private Board board;
        private int review_star;
        private int small_category_no;

        @Builder
        public ReviewSaveRequestDto(Board board, int review_star, int small_category_no) {
            this.board = board;
            this.review_star = review_star;
            this.small_category_no = small_category_no;
        }

        public Review toEntity(){
            return Review.builder()
                    .board(board)
                    .review_star(review_star)
                    .small_category_no(small_category_no)
                    .build();
        }
    }

    /** update review dto */
    @Getter
    @NoArgsConstructor
    public static class ReviewUpdateRequestDto {
        private Board board;
        private int review_star;
        private int small_category_no;

        @Builder
        public ReviewUpdateRequestDto(Board board, int review_star, int small_category_no) {
            this.board = board;
            this.review_star = review_star;
            this.small_category_no = small_category_no;
        }
    }

    /** select review dto */
    @Getter
    @Setter
    public static class ReviewResponseDto {
        private int review_no;
        private Board board;
        private int review_star;
        private int small_category_no;

        public ReviewResponseDto(Review review) {
            this.review_no = review.getReview_no();
            this.board = review.getBoard();
            this.review_star = review.getReview_star();
            this.small_category_no = review.getSmall_category_no();
        }
    }
}
