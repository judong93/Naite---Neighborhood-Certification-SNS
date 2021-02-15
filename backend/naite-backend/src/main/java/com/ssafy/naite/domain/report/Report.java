package com.ssafy.naite.domain.report;


import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportNo;

    private int boardNo;
    private int commentNo;
    private int userNo;

    @Column(nullable = false)
    private int reportType;

    @Column(nullable = false)
    private int reportTargetNo;

    private Timestamp reportDate;

    @Builder
    public Report(int reportNo, int boardNo, int commentNo, int userNo, int reportType, int reportTargetNo, Timestamp reportDate) {
        this.reportNo = reportNo;
        this.boardNo = boardNo;
        this.commentNo = commentNo;
        this.userNo = userNo;
        this.reportType = reportType;
        this.reportTargetNo = reportTargetNo;
        this.reportDate = reportDate;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportNo=" + reportNo +
                ", boardNo=" + boardNo +
                ", commentNo=" + commentNo +
                ", userNo=" + userNo +
                ", reportType=" + reportType +
                ", reportTargetNo=" + reportTargetNo +
                ", reportDate=" + reportDate +
                '}';
    }
}
