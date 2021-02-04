package com.ssafy.naite.service.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.market.Market;
import com.ssafy.naite.domain.market.MarketRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.dto.market.MarketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final BoardRepository boardRepository;

    private final Comparator<Market> comp = (m1,m2) -> m2.getBoard().getBoardCreatedAt().compareTo(m1.getBoard().getBoardCreatedAt());

    /**
     * 장터 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarkets() {
        return marketRepository.findAll().stream().filter(market -> market.getBoard().getBoardIsDeleted() == 0).sorted(comp).map(MarketDto.MarketResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 장터 게시글 세부 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarketsByCategory(int smallCategoryNo) {
        return marketRepository.findAll().stream().filter(market -> market.getBoard().getBoardIsDeleted() == 0).filter(market -> market.getSmallCategoryNo() == smallCategoryNo).sorted(comp).map(MarketDto.MarketResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 장터 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public MarketDto.MarketResponseDto findMarketById(int marketNo) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[market_no=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        return new MarketDto.MarketResponseDto(market);
    }

    /**
     * 장터 게시글 등록
     */
    @Transactional
    public int insertMarket(MarketDto.MarketSaveRequestDto marketSaveRequestDto, int userNo) {
        return marketRepository.save(marketSaveRequestDto.toEntity(userNo)).getMarketNo();
    }

    /**
     * 장터 게시글 수정
     */
    @Transactional
    public int updateMarket(int marketNo, MarketDto.MarketUpdateRequestDto marketUpdateRequestDto, int userNo) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[market_no=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));

        Board newBoard = marketUpdateRequestDto.getBoard();
        newBoard.setBoardNo(market.getBoard().getBoardNo());
        Board board = boardRepository.findById(newBoard.getBoardNo()).orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
        if(board.getUserNo() != userNo) {
            return -1;
        }
        market.update(marketUpdateRequestDto.getSmallCategoryNo(), marketUpdateRequestDto.getMarketCost(), marketUpdateRequestDto.getMarketIsCompleted());
        board.update(newBoard.getBoardTitle(), newBoard.getBoardContent(), newBoard.getBoardPic(), newBoard.getUnknownFlag(), newBoard.getOpenFlag());

        return marketNo;
    }

    /**
     * 유저별 장터 게시글 수 조회
     */
    public int getMarketByUser(int userNo) {
        List<Board> boards = marketRepository.getMarketsByUserNo(userNo);
        return boards.size();
    }

    /**
     * 유저별 장터 게시글 조회
     */
    public List<MarketDto.MarketResponseDto> getMarketListByUser(int userNo) {
        return marketRepository.findAll()
                .stream()
                .filter(market -> market.getBoard().getUserNo() == userNo)
                .map(MarketDto.MarketResponseDto::new)
                .collect(Collectors.toList());
    }
}
