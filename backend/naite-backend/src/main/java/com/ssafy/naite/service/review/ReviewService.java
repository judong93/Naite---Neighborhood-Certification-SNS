package com.ssafy.naite.service.review;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.review.Review;
import com.ssafy.naite.domain.review.ReviewRepository;
import com.ssafy.naite.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BoardRepository boardRepository;

    /** 리뷰 게시글 전체 조회 */
    @Transactional(readOnly = true)
    public List<ReviewDto.ReviewResponseDto> findAllReviews() {
        return reviewRepository.findAll().stream().filter(review -> review.getBoard().getBoard_is_deleted() == 0).map(ReviewDto.ReviewResponseDto::new).collect(Collectors.toList());
    }

    /** 리뷰 게시글 세부 카테고리별 조회 */
    @Transactional(readOnly = true)
    public List<ReviewDto.ReviewResponseDto> findAllReviewsByCategory(int small_category_no) {
        return reviewRepository.findAll().stream().filter(review -> review.getSmall_category_no() == small_category_no).map(ReviewDto.ReviewResponseDto::new).collect(Collectors.toList());
    }

    /** 리뷰 게시글 상세 조회 */
    @Transactional(readOnly = true)
    public ReviewDto.ReviewResponseDto findReviewById(int review_no) {
        Review review = reviewRepository.findById(review_no)
                .orElseThrow(()-> new IllegalAccessError("[review_no=" + review_no + "] 해당 게시글이 존재하지 않습니다."));

        return new ReviewDto.ReviewResponseDto(review);
    }

    /** 리뷰 게시글 등록 */
    @Transactional
    public int insertReview(ReviewDto.ReviewSaveRequestDto reviewSaveRequestDto) {
        return reviewRepository.save(reviewSaveRequestDto.toEntity()).getReview_no();
    }

    /** 리뷰 게시글 수정 */
    @Transactional
    public int updateReview(int review_no, ReviewDto.ReviewUpdateRequestDto reviewUpdateRequestDto) {
        Review review = reviewRepository.findById(review_no)
                                        .orElseThrow(()-> new IllegalAccessError("[review_no=" + review_no + "] 해당 게시글이 존재하지 않습니다."));
        review.update(reviewUpdateRequestDto.getReview_star(), reviewUpdateRequestDto.getSmall_category_no());

        Board newBoard = reviewUpdateRequestDto.getBoard();

        Board board = boardRepository.findById(newBoard.getBoard_no())
                                    .orElseThrow(()-> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
        board.update(newBoard.getBoard_title(),newBoard.getBoard_content(),newBoard.getBoard_pic(),newBoard.getUnknown_flag(),newBoard.getOpen_flag());

        return review_no;
    }
}
