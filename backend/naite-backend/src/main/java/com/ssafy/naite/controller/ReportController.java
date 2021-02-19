package com.ssafy.naite.controller;

import com.ssafy.naite.dto.report.ReportDto;
import com.ssafy.naite.dto.util.Response;
import com.ssafy.naite.service.report.ReportService;
import com.ssafy.naite.service.user.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/report")
@AllArgsConstructor
@Api(value = "신고")
public class ReportController {

    private final ReportService reportService;
    private final JwtService jwtService;

    @PostMapping("/insert")
    @ApiOperation(value = "신고 등록",
            notes = "[boardNo/commentNo] 신고받는 게시물 혹은 댓글 번호 <b>둘 중 하나는 반드시 0</b>이어야 합니다!\n" +
                    "[userNo] 신고자 인덱스 번호\n" +
                    "[reportTargetNo] 신고받는 회원 인덱스 번호\n" +
                    "[reportType] 0:게시물 1:댓글\n" +
                    "신고 등록 후 신고 번호를 리턴합니다.")
    public Response insertReport(@RequestBody ReportDto.ReportSaveRequestDto reportSaveRequestDto, HttpServletRequest req) {
        int insertedReportNo;
        try{
            int userNo = getUserNo(req);
            insertedReportNo = reportService.insertReport(userNo, reportSaveRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "신고 등록 성공", insertedReportNo);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
