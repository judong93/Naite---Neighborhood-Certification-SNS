package com.ssafy.naite.controller;

import com.ssafy.naite.dto.market.MarketDto;
import com.ssafy.naite.dto.util.Response;
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
    @ApiOperation(value = "장터 게시글 전체 조회", notes = "<b>토큰 필요</b>")
    public ResponseEntity<List<MarketDto.MarketResponseDto>> findAllMarkets(HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<MarketDto.MarketResponseDto> marketResponseDtoList = marketService.findAllMarkets(userNo);
        return new ResponseEntity<List<MarketDto.MarketResponseDto>>(marketResponseDtoList, HttpStatus.OK);
    }

    /**
     * 장터 게시글 카테고리별 조회
     */
    @GetMapping("/list/{smallCategoryNo}")
    @ApiOperation(value = "장터 게시글 카테고리별 조회", notes = "<b>토큰 필요</b>")
    public ResponseEntity<List<MarketDto.MarketResponseDto>> findAllMarketsByCategory(@PathVariable int smallCategoryNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<MarketDto.MarketResponseDto> marketResponseDtoList = marketService.findAllMarketsByCategory(smallCategoryNo, userNo);
        return new ResponseEntity<List<MarketDto.MarketResponseDto>>(marketResponseDtoList, HttpStatus.OK);
    }

    /**
     * 장터 게시글 유저별 조회
     */
    @GetMapping("/list/user/{userNo}")
    @ApiOperation(value = "장터 게시글 유저별 조회")
    public ResponseEntity<List<MarketDto.MarketByUserResponseDto>> findAllMarketsByUser(@PathVariable int userNo) {
        List<MarketDto.MarketByUserResponseDto> result = marketService.getMarketListByUser(userNo);
        return new ResponseEntity<List<MarketDto.MarketByUserResponseDto>>(result, HttpStatus.OK);
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
    @ApiOperation(value = "장터 게시글 등록", notes = "<b>토큰 필요</b>")
    public ResponseEntity<Integer> insertMarket(@ModelAttribute MarketDto.MarketSaveRequestDto marketSaveRequestDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        try {
            int insertedMarketNo = marketService.insertMarket(marketSaveRequestDto, userNo);
            return new ResponseEntity<Integer>(insertedMarketNo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Integer>(-1, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * 장터 게시글 수정
     */
    @PutMapping("/update/{marketNo}")
    @ApiOperation(value = "장터 게시글 수정", notes = "<b>토큰 필요</b>")
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
    @ApiOperation(value = "장터 모집 완료", notes = "<b>토큰 필요</b>")
    public ResponseEntity<String> completeMarket(@PathVariable int marketNo, @RequestBody MarketDto.MarketCompleteRequestDto marketCompleteRequestDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        String result = marketService.completeMarket(marketNo, userNo, marketCompleteRequestDto);
        if(!result.equals("success")) {
            return new ResponseEntity<String>(result, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<String>("모집 완료 갱신 성공", HttpStatus.CREATED);
    }

    /**
     * 장터 모집 완료 취소
     */
    @PutMapping("/restore/{marketNo}")
    @ApiOperation(value = "장터 모집 완료 취소")
    public ResponseEntity<Integer>  restoreMarket(@PathVariable int marketNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int restoredMarketNo = marketService.restoreMarket(marketNo, userNo);
        if(restoredMarketNo < 0) {
            return new ResponseEntity<Integer>(restoredMarketNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(restoredMarketNo, HttpStatus.CREATED);
    }

    /**
     * 장터 거래 채팅 시작
     */
    @PostMapping("/join/{marketNo}")
    @ApiOperation(value = "장터 거래 시작", notes = "<b>토큰 필요</b>")
    public Response joinMarket(@PathVariable int marketNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        try {
            marketService.joinMarket(userNo, marketNo);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "장터 거래가 성공적으로 시작되었습니다.", null);
    }

    /**
     * 거래 참여자 목록 조회
     */
    @GetMapping("/join/{marketNo}")
    @ApiOperation(value = "거래 참여자 목록 조회", notes = "<b>토큰 필요</b>")
    public Response getBuyers(@PathVariable int marketNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<String> buyerNick;
        try {
            buyerNick = marketService.getBuyers(userNo, marketNo);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "거래 참여자 목록 조회 성공", buyerNick);
    }

    /**
     * 구매자 평가
     */
    @PostMapping("/eval/{marketNo}")
    @ApiOperation(value = "구매자가 판매자를 평가", notes = "<b>토큰 필요</b>")
    public ResponseEntity<String> evalSellerFromBuyer(@PathVariable int marketNo, @RequestBody MarketDto.EvalSellerFromBuyerRequestDto dto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        String result = marketService.evalSellerFromBuyer(userNo, marketNo, dto);
        if(!result.equals("success")) {
            return new ResponseEntity<String>(result, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<String>("구매자의 판매자 평가 성공", HttpStatus.CREATED);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }

}
