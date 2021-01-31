package com.ssafy.naite.controller;

import com.ssafy.naite.dto.market.MarketDto;
import com.ssafy.naite.service.market.MarketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/market")
@AllArgsConstructor
@Api(value = "장터 게시판")
public class MarketController {

    private final MarketService marketService;

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
    public ResponseEntity<Integer> insertMarket(@RequestBody MarketDto.MarketSaveRequestDto marketSaveRequestDto) {
        int insertedMarketNo = marketService.insertMarket(marketSaveRequestDto);
        return new ResponseEntity<Integer>(insertedMarketNo, HttpStatus.CREATED);
    }

    /**
     * 장터 게시글 수정
     */
    @PutMapping("/update/{marketNo}")
    @ApiOperation(value = "장터 게시글 수정")
    public ResponseEntity<Integer> updateMarket(@PathVariable int marketNo, @RequestBody MarketDto.MarketUpdateRequestDto marketUpdateRequestDto) {
        int updatedMarketNo = marketService.updateMarket(marketNo, marketUpdateRequestDto);
        return new ResponseEntity<Integer>(updatedMarketNo, HttpStatus.CREATED);
    }
}
