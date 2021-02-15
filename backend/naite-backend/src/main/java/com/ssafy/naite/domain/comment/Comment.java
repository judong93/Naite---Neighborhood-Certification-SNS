package com.ssafy.naite.domain.comment;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentNo;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_no")
    private Board board;

    @Column(length = 200)
    private String commentContent;

    private LocalDateTime commentCreatedAt;
    private LocalDateTime commentUpdatedAt;
    private Byte commentIsDeleted;
    private Integer commentReportCnt;
    private Integer commentParentId;
    private Byte commentIsUnknown;

    public Comment(int commentNo){
        this.commentNo = commentNo;
    }

    @Builder
    public Comment(Integer commentNo, User user, Board board, String commentContent, LocalDateTime commentCreatedAt, LocalDateTime commentUpdatedAt, Byte commentIsDeleted, Integer commentReportCnt, Integer commentParentId, Byte commentIsUnknown) {
        this.commentNo = commentNo;
        this.board = board;
        this.user = user;
        this.commentContent = commentContent;
        this.commentCreatedAt = commentCreatedAt;
        this.commentUpdatedAt = commentUpdatedAt;
        this.commentIsDeleted = commentIsDeleted;
        this.commentReportCnt = commentReportCnt;
        this.commentParentId = commentParentId;
        this.commentIsUnknown = commentIsUnknown;
    }

    public void updateContent(String content) {
        this.commentContent = content;
    }

    public void updateDelete() {
        this.commentIsDeleted = 1;
    }

    public void updateTime() {
        this.commentUpdatedAt = LocalDateTime.now();
    }

    public void updateReportCnt(){
        if (this.commentReportCnt < 100)
            this.commentReportCnt += 1;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentNo=" + commentNo +
                ", user=" + user +
                ", board=" + board +
                ", commentContent='" + commentContent + '\'' +
                ", commentCreatedAt=" + commentCreatedAt +
                ", commentUpdatedAt=" + commentUpdatedAt +
                ", commentIsDeleted=" + commentIsDeleted +
                ", commentReportCnt=" + commentReportCnt +
                ", commentParentId=" + commentParentId +
                '}';
    }
}
