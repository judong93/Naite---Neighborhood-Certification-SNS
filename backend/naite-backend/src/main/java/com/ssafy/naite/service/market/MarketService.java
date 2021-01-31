package com.ssafy.naite.service.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.market.Market;
import com.ssafy.naite.domain.market.MarketRepository;
import com.ssafy.naite.dto.market.MarketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final BoardRepository boardRepository;

    /**
     * 장터 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarkets() {
        return marketRepository.findAll().stream().filter(market -> market.getBoard().getBoardIsDeleted() == 0).map(MarketDto.MarketResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 장터 게시글 세부 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarketsByCategory(int smallCategoryNo) {
        return marketRepository.findAll().stream().filter(market -> market.getBoard().getBoardIsDeleted() == 0).filter(market -> market.getSmallCategoryNo() == smallCategoryNo).map(MarketDto.MarketResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 장터 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public MarketDto.MarketResponseDto findMarketById(int marketNo) {
        Market market = marketRepository.findById(marketNo)
                .orElseThrow(() -> new IllegalAccessError("[market_no=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        return new MarketDto.MarketResponseDto(market);
    }

    /**
     * 장터 게시글 등록
     */
    @Transactional
    public int insertMarket(MarketDto.MarketSaveRequestDto marketSaveRequestDto) {
        return marketRepository.save(marketSaveRequestDto.toEntity()).getMarketNo();
    }

    /**
     * 장터 게시글 수정
     */
    @Transactional
    public int updateMarket(int marketNo, MarketDto.MarketUpdateRequestDto marketUpdateRequestDto) {
        Market market = marketRepository.findById(marketNo)
                .orElseThrow(() -> new IllegalAccessError("[market_no=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        market.update(marketUpdateRequestDto.getSmallCategoryNo(), marketUpdateRequestDto.getMarketCost(), marketUpdateRequestDto.getMarketIsCompleted(), marketUpdateRequestDto.getMarketPlace(), marketUpdateRequestDto.getMarketPerson(), marketUpdateRequestDto.getTime());

        Board newBoard = marketUpdateRequestDto.getBoard();
        Board board = boardRepository.findById(newBoard.getBoardNo())
                .orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
        board.update(newBoard.getBoardTitle(), newBoard.getBoardContent(), newBoard.getBoardPic(), newBoard.getUnknownFlag(), newBoard.getOpenFlag());

        return marketNo;
    }
}
