package com.ssafy.naite.controller;

import com.ssafy.naite.dto.market.MarketDto;
import com.ssafy.naite.service.market.MarketService;
import com.ssafy.naite.service.user.JwtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/market")
@AllArgsConstructor
@Api(value = "장터 게시판")
public class MarketController {

    private final MarketService marketService;
    private final JwtService jwtService;

    /**
     * 장터 게시글 전체 조회
     */
    @GetMapping("/list")
    @ApiOperation(value = "장터 게시글 전체 조회")
    public ResponseEntity<List<MarketDto.MarketResponseDto>> findAllMarkets() {
        List<MarketDto.MarketResponseDto> marketResponseDtoList = marketService.findAllMarkets();
        return new ResponseEntity<List<MarketDto.MarketResponseDto>>(marketResponseDtoList, HttpStatus.OK);
    }

    /**
     * 장터 게시글 카테고리별 조회
     */
    @GetMapping("/list/{smallCategoryNo}")
    @ApiOperation(value = "장터 게시글 카테고리별 조회")
    public ResponseEntity<List<MarketDto.MarketResponseDto>> findAllMarketsByCategory(@PathVariable int smallCategoryNo) {
        List<MarketDto.MarketResponseDto> marketResponseDtoList = marketService.findAllMarketsByCategory(smallCategoryNo);
        return new ResponseEntity<List<MarketDto.MarketResponseDto>>(marketResponseDtoList, HttpStatus.OK);
    }

    /**
     * 장터 게시글 유저별 조회
     */
    @GetMapping("/list/user/{userNo}")
    @ApiOperation(value = "장터 게시글 유저별 조회")
    public ResponseEntity<List<MarketDto.MarketResponseDto>> findAllMarketsByUser(@PathVariable int userNo) {
        List<MarketDto.MarketResponseDto> result = marketService.getMarketListByUser(userNo);
        return new ResponseEntity<List<MarketDto.MarketResponseDto>>(result, HttpStatus.OK);
    }

    /**
     * 장터 게시글 상세 조회
     */
    @GetMapping("/detail/{marketNo}")
    @ApiOperation(value = "장터 게시글 상세 조회")
    public ResponseEntity<MarketDto.MarketResponseDto> findMarketById(@PathVariable int marketNo) {
        MarketDto.MarketResponseDto marketResponseDto = marketService.findMarketById(marketNo);
        return new ResponseEntity<MarketDto.MarketResponseDto>(marketResponseDto, HttpStatus.OK);
    }

    /**
     * 장터 게시글 등록
     */
    @PostMapping("/insert")
    @ApiOperation(value = "장터 게시글 등록")
    public ResponseEntity<Integer> insertMarket(@RequestBody MarketDto.MarketSaveRequestDto marketSaveRequestDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int insertedMarketNo = marketService.insertMarket(marketSaveRequestDto, userNo);
        return new ResponseEntity<Integer>(insertedMarketNo, HttpStatus.CREATED);
    }

    /**
     * 장터 게시글 수정
     */
    @PutMapping("/update/{marketNo}")
    @ApiOperation(value = "장터 게시글 수정")
    public ResponseEntity<Integer> updateMarket(@PathVariable int marketNo, @RequestBody MarketDto.MarketUpdateRequestDto marketUpdateRequestDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int updatedMarketNo = marketService.updateMarket(marketNo, marketUpdateRequestDto, userNo);
        if(updatedMarketNo < 0) {
            return new ResponseEntity<Integer>(updatedMarketNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(updatedMarketNo, HttpStatus.CREATED);
    }

    /**
     * 장터 모집 완료
     */
    @PutMapping("/complete/{marketNo}")
    @ApiOperation(value = "장터 모집 완료")
    public ResponseEntity<Integer> completeMarket(@PathVariable int marketNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int completedMarketNo = marketService.completeMarket(marketNo, userNo);
        if(completedMarketNo < 0) {
            return new ResponseEntity<Integer>(completedMarketNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(completedMarketNo, HttpStatus.CREATED);
    }

    /**
     * 장터 모집 완료 취소
     */
    @PutMapping("/restore/{marketNo}")
    @ApiOperation(value = "장터 모집 완료")
    public ResponseEntity<Integer>  restoreMarket(@PathVariable int marketNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int restoredMarketNo = marketService.restoreMarket(marketNo, userNo);
        if(restoredMarketNo < 0) {
            return new ResponseEntity<Integer>(restoredMarketNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(restoredMarketNo, HttpStatus.CREATED);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
