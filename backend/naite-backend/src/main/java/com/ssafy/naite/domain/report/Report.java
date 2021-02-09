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

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "comment_no")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Column(nullable = false)
    private int reportType;

    @Column(nullable = false)
    private int reportTargetNo;

    private Timestamp reportDate;

    @Builder
    public Report(int reportNo, Board board, Comment comment, User user, int reportType, int reportTargetNo, Timestamp reportDate) {
        this.reportNo = reportNo;
        this.board = board;
        this.comment = comment;
        this.user = user;
        this.reportType = reportType;
        this.reportTargetNo = reportTargetNo;
        this.reportDate = reportDate;
    }


}
