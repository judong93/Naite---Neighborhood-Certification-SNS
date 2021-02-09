package com.ssafy.naite.controller;

import com.ssafy.naite.dto.report.ReportDto;
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
    @ApiOperation(value = "신고 등록")
    public ResponseEntity<Integer> insertReport(@RequestBody ReportDto.ReportSaveRequestDto reportSaveRequestDto, HttpServletRequest req){
        int userNo = getUserNo(req);
        int insertedReportNo = reportService.insertReport(userNo, reportSaveRequestDto);
        return new ResponseEntity<Integer>(insertedReportNo, HttpStatus.CREATED);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
