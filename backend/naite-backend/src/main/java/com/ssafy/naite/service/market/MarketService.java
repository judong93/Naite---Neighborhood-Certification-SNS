package com.ssafy.naite.service.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.like.LikeRepository;
import com.ssafy.naite.domain.market.Market;
import com.ssafy.naite.domain.market.MarketRepository;
import com.ssafy.naite.domain.picture.Picture;
import com.ssafy.naite.domain.picture.PictureRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.domain.village.VillageRepository;
import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.dto.market.MarketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PictureRepository pictureRepository;
    private final VillageRepository villageRepository;

    private final Comparator<Market> comp = (m1,m2) -> m2.getBoard().getBoardCreatedAt().compareTo(m1.getBoard().getBoardCreatedAt());

    /**
     * 장터 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarkets(int userNo) {
//        String userVillageName = villageRepository.findByUserNo(userNo).get().getVillageName();
        return marketRepository.findAll()
                .stream()
                .filter(market -> market.getBoard().getBoardIsDeleted() == 0)
//                .filter(market -> villageRepository.findByUserNo(market.getBoard().getUserNo()).get().getVillageName().equals(userVillageName))
                .sorted(comp)
                .map(MarketDto.MarketResponseDto::new)
                .map(marketResponseDto -> {
                    marketResponseDto.setUserNick(userRepository.findById(marketResponseDto.getBoard().getUserNo()).get().getUserNick());
                    marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
                    marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
                    return marketResponseDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 장터 게시글 세부 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarketsByCategory(int smallCategoryNo, int userNo) {
//        String userVillageName = villageRepository.findByUserNo(userNo).get().getVillageName();
        return marketRepository.findAll()
                .stream()
                .filter(market -> market.getBoard().getBoardIsDeleted() == 0)
                .filter(market -> market.getSmallCategoryNo() == smallCategoryNo)
//                .filter(market -> villageRepository.findByUserNo(market.getBoard().getUserNo()).get().getVillageName().equals(userVillageName))
                .sorted(comp)
                .map(MarketDto.MarketResponseDto::new)
                .map(marketResponseDto -> {
                    marketResponseDto.setUserNick(userRepository.findById(marketResponseDto.getBoard().getUserNo()).get().getUserNick());
                    marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
                    marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
                    return marketResponseDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 장터 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public MarketDto.MarketResponseDto findMarketById(int marketNo) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[market_no=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        MarketDto.MarketResponseDto marketResponseDto = new MarketDto.MarketResponseDto(market);
        marketResponseDto.setUserNick(userRepository.findById(marketResponseDto.getBoard().getUserNo()).get().getUserNick());

        List<BoardDto.LikeResponseDto> likeResponseDtoList = likeRepository.findAll().stream().filter(boardLike -> boardLike.getBoardNo() == marketResponseDto.getBoard().getBoardNo()).map(BoardDto.LikeResponseDto::new).collect(Collectors.toList());
        List<String> likeUserList = new ArrayList<String>();
        for (BoardDto.LikeResponseDto likeResponseDto : likeResponseDtoList) {
            likeUserList.add(userRepository.findById(likeResponseDto.getUserNo()).get().getUserNick());
        }
        marketResponseDto.setUsersWithLike(likeUserList);
        marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
        marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
        return marketResponseDto;
    }

    /**
     * 장터 게시글 등록
     */
    @Transactional
    public int insertMarket(MarketDto.MarketSaveRequestDto marketSaveRequestDto, int userNo) throws IOException {
        Board board = Board.builder()
                .userNo(userNo)
                .bigCategoryNo(marketSaveRequestDto.getBigCategoryNo())
                .boardTitle(marketSaveRequestDto.getBoardTitle())
                .boardContent(marketSaveRequestDto.getBoardContent())
                .boardPic(marketSaveRequestDto.getBoardPic())
                .boardCreatedAt(LocalDateTime.now())
                .openFlag(marketSaveRequestDto.getOpenFlag())
                .unknownFlag(marketSaveRequestDto.getUnknownFlag())
                .build();
        int insertedMarketNo = marketRepository.save(marketSaveRequestDto.toEntity(board)).getMarketNo();
        int insertedBoardNo = marketRepository.findById(insertedMarketNo).get().getBoard().getBoardNo();
        if(marketSaveRequestDto.getFiles() != null) {
            String rootPath = "/home/ubuntu/images/board/";
            String apiPath = "https://i4a402.p.ssafy.io/images/board/";
            List<MultipartFile> files = marketSaveRequestDto.getFiles();
            for(MultipartFile file : files) {
                String changeName = insertedBoardNo + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSSS")) + "_" + file.getOriginalFilename();
                String filePath = rootPath + changeName;
                System.out.println(filePath);
                File dest = new File(filePath);
                file.transferTo(dest);
                pictureRepository.save(Picture.builder().boardNo(insertedBoardNo).boardPic(apiPath + changeName).build());
            }
        }
        return insertedMarketNo;
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
    @Transactional
    public List<MarketDto.MarketResponseDto> getMarketListByUser(int userNo) {
        return marketRepository.findAll()
                .stream()
                .filter(market -> market.getBoard().getUserNo() == userNo)
                .filter(market -> market.getBoard().getBoardIsDeleted() == 0)
                .sorted(comp)
                .map(MarketDto.MarketResponseDto::new)
                .map(marketResponseDto -> {
                    marketResponseDto.setUserNick(userRepository.findById(userNo).get().getUserNick());
                    marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
                    marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
                    return marketResponseDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 장터 모집 완료
     */
    @Transactional
    public int completeMarket(int marketNo, int userNo) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[marketNo=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        if(market.getBoard().getUserNo() != userNo) {
            return -1;
        }
        market.marketClose(1);
        marketRepository.save(market);
        return marketNo;
    }

    /**
     * 장터 모집 완료 취소
     */
    @Transactional
    public int restoreMarket(int marketNo, int userNo) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[marketNo=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        if(market.getBoard().getUserNo() != userNo) {
            return -1;
        }
        market.marketClose(0);
        marketRepository.save(market);
        return marketNo;
    }
}
