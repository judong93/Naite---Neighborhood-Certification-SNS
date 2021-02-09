package com.ssafy.naite.dto.report;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.report.Report;
import com.ssafy.naite.domain.user.User;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class ReportDto {

    @Getter
    @NoArgsConstructor
    public static class ReportSaveRequestDto{

        @ApiParam(value = "게시글 인덱스")
        private int boardNo;
        @ApiParam(value = "댓글 인덱스")
        private int commentNo;
        @ApiParam(value = "신고 타입 (0 : 게시글, 1 : 댓글)")
        private int reportType;
        @ApiParam(value = "신고 당한 사람 인덱스")
        private int reportTargetNo;

        @Builder
        public ReportSaveRequestDto(int boardNo, int commentNo, int reportType, int reportTargetNo) {
            this.boardNo = boardNo;
            this.commentNo = commentNo;
            this.reportType = reportType;
            this.reportTargetNo = reportTargetNo;
        }
//        public Report toEntity(int userNo){
//           return Report.builder()
//                   .board(new Board(boardNo))
//                   .comment(new Comment(commentNo))
//                   .user(new User(userNo))
//                   .reportType(reportType)
//                   .reportTargetNo(reportTargetNo)
//                   .reportDate(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
//                   .build();
//        }
    }
}
