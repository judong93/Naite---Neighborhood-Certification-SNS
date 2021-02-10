package com.ssafy.naite.domain.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT r FROM Board b, Report r WHERE b.boardNo = r.boardNo and r.userNo = :userNo")
    public Optional<Report> findByBoardNoAndUserNo(int userNo);
    @Query("SELECT r FROM Comment c, Report r WHERE c.commentNo = r.commentNo and r.userNo = :userNo")
    public Optional<Report> findByCommentNoAndUserNo(int userNo);
}
