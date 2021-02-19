package com.ssafy.naite.service.report;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.report.Report;
import com.ssafy.naite.domain.report.ReportRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.report.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    /**
     * 신고 등록
     */
    @Transactional
    public int insertReport(int userNo, ReportDto.ReportSaveRequestDto reportSaveRequestDto) throws Exception {
        // 1. 신고 타입 판별, 0:게시물 1:댓글
        int reportType = reportSaveRequestDto.getReportType();
        Report report;

        if (reportType == 0) { // 게시물

            // 2. 해당 게시물을 신고한 회원인지 판별
            // 게시물 번호(boardNo)와 신고자 번호(userNo)로 신고 조회

            System.out.println("신고 게시글 번호 : " + reportSaveRequestDto.getBoardNo());
            System.out.println("신고자 : " + userNo);
            Optional<Report> existed = reportRepository.findByBoardNoAndUserNo(reportSaveRequestDto.getBoardNo(), userNo);

            if (existed.isPresent())
                throw new Exception("이미 신고한 게시물입니다.");
            else {  // 해당 신고자가 게시물을 신고한 기록이 없으면 신고 가능
                report = Report.builder()
                        .boardNo(reportSaveRequestDto.getBoardNo())
                        .userNo(userNo)
                        .reportType(reportSaveRequestDto.getReportType())
                        .reportTargetNo(reportSaveRequestDto.getReportTargetNo())
                        .reportDate(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                        .build();

                System.out.println(report.toString());

                // 3. 10회 이상 신고 받을 시, 삭제
                // 신고받은 게시물의 현재 신고 수 조회
                Board reportedBoard = boardRepository.findById(reportSaveRequestDto.getBoardNo()).orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
                User reportedUser = userRepository.findById(reportSaveRequestDto.getReportTargetNo()).orElseThrow(() -> new IllegalAccessError("해당 사용자가 존재하지 않습니다."));
                int boardReportCnt = reportedBoard.getBoardReportCnt();

                if (boardReportCnt >= 9) {
                    // 신고횟수 10회 이상 : 게시물 삭제, 신뢰도 - 1
                    reportedBoard.delete(1);
                    reportedUser.updateScore();
                }else {
                    // 신고수 10회 미만 : 신고횟수 + 1 로 게시물 업데이트
                    reportedBoard.updateReportCnt();
                }
            }
        } else { // 댓글
            Optional<Report> existed = reportRepository.findByCommentNoAndUserNo(reportSaveRequestDto.getCommentNo(), userNo);
            if (existed.isPresent())
                throw new Exception("이미 신고한 댓글입니다.");
            else {  // 해당 신고자가 게시물을 신고한 기록이 없으면 신고 가능
                report = Report.builder()
                        .commentNo(reportSaveRequestDto.getCommentNo())
                        .userNo(userNo)
                        .reportType(reportSaveRequestDto.getReportType())
                        .reportTargetNo(reportSaveRequestDto.getReportTargetNo())
                        .reportDate(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                        .build();

                Comment reportedComment = commentRepository.findById(reportSaveRequestDto.getCommentNo()).orElseThrow(() -> new IllegalAccessError("해당 댓글이 존재하지 않습니다."));
                User reportedUser = userRepository.findById(reportSaveRequestDto.getReportTargetNo()).orElseThrow(() -> new IllegalAccessError("해당 사용자가 존재하지 않습니다."));
                int commentReportCnt = reportedComment.getCommentReportCnt();
                if (commentReportCnt >= 9) {
                    reportedComment.updateDelete();
                    reportedComment.updateTime();
                    reportedUser.updateScore();
                }else {
                    reportedComment.updateReportCnt();
                }
            }
        }
        int reportNo = reportRepository.save(report).getReportNo();


        return reportNo;
    }


}