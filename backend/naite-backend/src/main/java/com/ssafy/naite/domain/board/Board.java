package com.ssafy.naite.domain.board;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardNo;

    @Column(nullable = false)
    private int userNo;

    @Column(nullable = false)
    private int bigCategoryNo;

    @Lob
    @Column(nullable = false)
    private String boardTitle;

    @Lob
    @Column(nullable = false)
    private String boardContent;

    private String boardPic;

    @Column(updatable = false)
    private LocalDateTime boardCreatedAt;

    @LastModifiedDate
    private LocalDateTime boardUpdatedAt;

    private int boardLikeCnt;

    @Column(nullable = false)
    private int unknownFlag;

    private int boardReportCnt;

    @Column(nullable = false)
    private int openFlag;

    @Column(nullable = false)
    private int boardIsDeleted;

    @Builder
    public Board(int userNo, int bigCategoryNo, String boardTitle, String boardContent, String boardPic,
                 LocalDateTime boardCreatedAt, LocalDateTime boardUpdatedAt, int boardLikeCnt,
                 int unknownFlag, int boardReportCnt, int openFlag, int boardIsDeleted) {
        this.userNo = userNo;
        this.bigCategoryNo = bigCategoryNo;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardPic = boardPic;
        this.boardCreatedAt = boardCreatedAt;
        this.boardUpdatedAt = boardUpdatedAt;
        this.boardLikeCnt = boardLikeCnt;
        this.unknownFlag = unknownFlag;
        this.boardReportCnt = boardReportCnt;
        this.openFlag = openFlag;
        this.boardIsDeleted = boardIsDeleted;
    }

    public void update(String boardTitle, String boardContent, String boardPic, int unknownFlag, int openFlag) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardPic = boardPic;
        this.boardUpdatedAt = LocalDateTime.now();
        this.unknownFlag = unknownFlag;
        this.openFlag = openFlag;
    }

    public void delete(int boardIsDeleted) {
        this.boardUpdatedAt = LocalDateTime.now();
        this.boardIsDeleted = boardIsDeleted;
    }

    public void like(boolean isLike) {
        if(isLike) {
            this.boardLikeCnt += 1;
        } else {
            this.boardLikeCnt -= 1;
            if(this.boardLikeCnt < 0) {
                this.boardLikeCnt = 0;
            }
        }
    }
}
