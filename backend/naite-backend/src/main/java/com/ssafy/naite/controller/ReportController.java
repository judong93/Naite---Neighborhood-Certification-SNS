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
            notes = "신고할 게시물 혹은 댓글 인덱스를 파라미터에 담아 보냅니다.\n" +
                    "boardNo와 commentNo 둘 중 하나는 0이 입력되어야 합니다.\n" +
                    "신고 등록 후 신고 번호를 리턴합니다.")
    public ResponseEntity<Map<String, Object>> insertReport(@RequestBody ReportDto.ReportSaveRequestDto reportSaveRequestDto, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try{
            int userNo = getUserNo(req);
            int insertedReportNo = reportService.insertReport(userNo, reportSaveRequestDto);
            resultMap.put("report-no", insertedReportNo);
            status = HttpStatus.CREATED;
        } catch (Exception e) {
            resultMap.put("message", "신고 실패");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
