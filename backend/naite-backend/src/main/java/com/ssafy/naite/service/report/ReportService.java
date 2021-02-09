package com.ssafy.naite.service.report;

import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.report.ReportRepository;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.dto.report.ReportDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    /** 신고 등록 */
    public int insertReport(int userNo, ReportDto.ReportSaveRequestDto reportSaveRequestDto){
        return reportRepository.save(reportSaveRequestDto.toEntity(userNo)).getReportNo();
    }



}
