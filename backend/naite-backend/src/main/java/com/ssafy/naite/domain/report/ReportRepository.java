package com.ssafy.naite.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT r FROM Report r WHERE r.boardNo = ?1 and r.userNo = ?2")
    public Optional<Report> findByBoardNoAndUserNo(int reportedBoardNo, int userNo);
    @Query("SELECT r FROM Report r WHERE r.commentNo = ?1 and r.userNo = ?2")
    public Optional<Report> findByCommentNoAndUserNo(int reportedCommentNo, int userNo);
}
