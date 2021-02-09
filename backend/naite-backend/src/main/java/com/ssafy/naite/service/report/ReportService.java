package com.ssafy.naite.service.report;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.report.Report;
import com.ssafy.naite.domain.report.ReportRepository;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.report.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public int insertReport(int userNo, ReportDto.ReportSaveRequestDto reportSaveRequestDto) throws Exception {
        // 1. 신고 타입 판별, 0:게시물 1:댓글
        int reportType = reportSaveRequestDto.getReportType();
        Report report;

        try {
            if (reportType == 0) { // 게시물

                // 2. 해당 게시물을 신고한 회원인지 판별
                // 게시물 번호(boardNo)와 신고자 번호(userNo)로 신고 조회
                Optional<Report> existed = reportRepository.findByBoardNoAndUserNo(userNo);
                if (existed.isPresent())
                    throw new Exception("이미 신고한 게시물입니다.");
                else {  // 해당 신고자가 게시물을 신고한 기록이 없으면 신고 가능
                    report = Report.builder()
                            .boardNo(reportSaveRequestDto.getBoardNo())
//                        .user(new User(userNo))
                            .userNo(userNo)
                            .reportType(reportSaveRequestDto.getReportType())
                            .reportTargetNo(reportSaveRequestDto.getReportTargetNo())
                            .reportDate(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                            .build();

                    // 신고받은 게시물 조회 후 신고 수 조회
                    Board reportedBoard = boardRepository.findById(reportSaveRequestDto.getBoardNo()).orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
                    int boardReportCnt = reportedBoard.getBoardReportCnt();

                    if (boardReportCnt >= 9) {
                        // 신고횟수 10회(9 + 1) 이상일 시 게시물 삭제
                        reportedBoard.delete(1);

                    }else {
                        // 신고횟수 + 1 로 게시물 업데이트
                    }

                }





            } else { // 댓글
                report = Report.builder()
                        .commentNo(reportSaveRequestDto.getCommentNo())
//                        .user(new User(userNo))
                        .userNo(userNo)
                        .reportType(reportSaveRequestDto.getReportType())
                        .reportTargetNo(reportSaveRequestDto.getReportTargetNo())
                        .reportDate(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                        .build();
            }
            int reportNo = reportRepository.save(report).getReportNo();


            return reportNo;
        } catch (Exception e) {
            throw new Exception("신고 등록 실패");
        }
    }


}
