package com.ssafy.naite.service.review;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.review.Review;
import com.ssafy.naite.domain.review.ReviewRepository;
import com.ssafy.naite.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BoardRepository boardRepository;

    private final Comparator<Review> comp = (r1, r2) -> r2.getBoard().getBoardCreatedAt().compareTo(r1.getBoard().getBoardCreatedAt());

    /**
     * 리뷰 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewDto.ReviewResponseDto> findAllReviews() {
        return reviewRepository.findAll().stream().filter(review -> review.getBoard().getBoardIsDeleted() == 0).sorted(comp).map(ReviewDto.ReviewResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 리뷰 게시글 세부 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<ReviewDto.ReviewResponseDto> findAllReviewsByCategory(int smallCategoryNo) {
        return reviewRepository.findAll().stream().filter(review -> review.getBoard().getBoardIsDeleted() == 0).filter(review -> review.getSmallCategoryNo() == smallCategoryNo).sorted(comp).map(ReviewDto.ReviewResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 리뷰 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public ReviewDto.ReviewResponseDto findReviewById(int reviewNo) {
        Review review = reviewRepository.findById(reviewNo).orElseThrow(() -> new IllegalAccessError("[review_no=" + reviewNo + "] 해당 게시글이 존재하지 않습니다."));
        return new ReviewDto.ReviewResponseDto(review);
    }

    /**
     * 리뷰 게시글 등록
     */
    @Transactional
    public int insertReview(ReviewDto.ReviewSaveRequestDto reviewSaveRequestDto, int userNo) {
        return reviewRepository.save(reviewSaveRequestDto.toEntity(userNo)).getReviewNo();
    }

    /**
     * 리뷰 게시글 수정
     */
    @Transactional
    public int updateReview(int review_no, ReviewDto.ReviewUpdateRequestDto reviewUpdateRequestDto, int userNo) {
        Review review = reviewRepository.findById(review_no).orElseThrow(() -> new IllegalAccessError("[review_no=" + review_no + "] 해당 게시글이 존재하지 않습니다."));

        Board newBoard = reviewUpdateRequestDto.getBoard();
        newBoard.setBoardNo(review.getBoard().getBoardNo());
        Board board = boardRepository.findById(newBoard.getBoardNo()).orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
        if(board.getUserNo() != userNo) {
            return -1;
        }
        review.update(reviewUpdateRequestDto.getReviewStar(), reviewUpdateRequestDto.getSmallCategoryNo());
        board.update(newBoard.getBoardTitle(), newBoard.getBoardContent(), newBoard.getBoardPic(), newBoard.getUnknownFlag(), newBoard.getOpenFlag());

        return review_no;
    }
}
