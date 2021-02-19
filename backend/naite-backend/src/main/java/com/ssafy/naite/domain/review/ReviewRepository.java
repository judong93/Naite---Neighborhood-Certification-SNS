package com.ssafy.naite.domain.review;

import com.ssafy.naite.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    @Query("SELECT r FROM Review r WHERE r.board = :board")
    public Optional<Review> findReviewByBoard(Board board);
}
