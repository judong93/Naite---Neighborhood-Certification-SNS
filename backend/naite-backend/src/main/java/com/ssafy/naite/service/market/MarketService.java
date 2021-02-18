package com.ssafy.naite.service.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.evaluation.Evaluation;
import com.ssafy.naite.domain.evaluation.EvaluationRepository;
import com.ssafy.naite.domain.like.LikeRepository;
import com.ssafy.naite.domain.market.Market;
import com.ssafy.naite.domain.market.MarketJoin;
import com.ssafy.naite.domain.market.MarketJoinRepository;
import com.ssafy.naite.domain.market.MarketRepository;
import com.ssafy.naite.domain.picture.Picture;
import com.ssafy.naite.domain.picture.PictureRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.domain.user.UserRepository;
import com.ssafy.naite.domain.village.Village;
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
import java.util.Optional;
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
    private final MarketJoinRepository marketJoinRepository;
    private final EvaluationRepository evaluationRepository;

    private final Comparator<Market> comp = (m1,m2) -> m2.getBoard().getBoardCreatedAt().compareTo(m1.getBoard().getBoardCreatedAt());

    /**
     * 장터 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarkets(int userNo) {
        String userVillageName = villageRepository.findByUserNo(userNo).orElseThrow(() -> new IllegalAccessError("주소가 없어요ㅜㅜ. 잘못된 계정입니다!")).getVillageName();
        return marketRepository.findAll()
                .stream()
                .filter(market -> market.getBoard().getBoardIsDeleted() == 0)
//                .filter(market -> villageRepository.findByUserNo(market.getBoard().getUserNo()).orElse(new Village(userNo, userVillageName)).getVillageName().equals(userVillageName))
                .sorted(comp)
                .map(MarketDto.MarketResponseDto::new)
                .map(marketResponseDto -> {
                    User user = userRepository.findById(marketResponseDto.getBoard().getUserNo()).get();
                    marketResponseDto.setUserNick(user.getUserNick());
                    marketResponseDto.setUserPic(user.getUserPic());
                    marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
                    marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
                    marketResponseDto.setUserScore(user.getUserScore());
                    return marketResponseDto;
                })
                .collect(Collectors.toList());
    }

    /**
     * 장터 게시글 세부 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<MarketDto.MarketResponseDto> findAllMarketsByCategory(int smallCategoryNo, int userNo) {
        String userVillageName = villageRepository.findByUserNo(userNo).orElseThrow(() -> new IllegalAccessError("주소가 없어요ㅜㅜ. 잘못된 계정입니다!")).getVillageName();
        return marketRepository.findAll()
                .stream()
                .filter(market -> market.getBoard().getBoardIsDeleted() == 0)
                .filter(market -> market.getSmallCategoryNo() == smallCategoryNo)
//                .filter(market -> villageRepository.findByUserNo(market.getBoard().getUserNo()).orElse(new Village(userNo, userVillageName)).getVillageName().equals(userVillageName))
                .sorted(comp)
                .map(MarketDto.MarketResponseDto::new)
                .map(marketResponseDto -> {
                    User user = userRepository.findById(marketResponseDto.getBoard().getUserNo()).get();
                    marketResponseDto.setUserNick(user.getUserNick());
                    marketResponseDto.setUserPic(user.getUserPic());
                    marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
                    marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
                    marketResponseDto.setUserScore(user.getUserScore());
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
        User user = userRepository.findById(marketResponseDto.getBoard().getUserNo()).get();
        marketResponseDto.setUserNick(user.getUserNick());
        marketResponseDto.setUserPic(user.getUserPic());

        List<BoardDto.LikeResponseDto> likeResponseDtoList = likeRepository.findAll().stream().filter(boardLike -> boardLike.getBoardNo() == marketResponseDto.getBoard().getBoardNo()).map(BoardDto.LikeResponseDto::new).collect(Collectors.toList());
        List<String> likeUserList = new ArrayList<String>();
        for (BoardDto.LikeResponseDto likeResponseDto : likeResponseDtoList) {
            likeUserList.add(userRepository.findById(likeResponseDto.getUserNo()).get().getUserNick());
        }
        marketResponseDto.setUsersWithLike(likeUserList);
        marketResponseDto.setBoardCommentCnt(commentRepository.findAll().stream().filter(comment -> comment.getBoard().getBoardNo() == marketResponseDto.getBoard().getBoardNo()).collect(Collectors.toList()).size());
        marketResponseDto.setFiles(pictureRepository.findPicByBoardNo(marketResponseDto.getBoard().getBoardNo()));
        marketResponseDto.setUserScore(user.getUserScore());
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
        List<Board> boards = marketRepository.getBoardsByUserNo(userNo);
        return boards.size();
    }

    /**
     * 유저별 장터 게시글 조회
     */
    @Transactional
    public List<MarketDto.MarketByUserResponseDto> getMarketListByUser(int userNo) {
        List<MarketDto.MarketByUserResponseDto> result = new ArrayList<>();
        List<Board> boardOfSeller = marketRepository.getBoardsByUserNo(userNo); // 본인 == 판매자
        List<Board> boardOfBuyer = marketRepository.getBoardsByMarketPerson(new User(userNo)); // 본인 == 구매자

        for (int i = 0; i < boardOfSeller.size(); i++) {
            Board board = boardOfSeller.get(i);
            Evaluation eval = evaluationRepository.findByMarketAndEvalIsSeller(marketRepository.findByBoard(board).get(), 0);
            int evalIsCompleted = 0;
            if (eval != null) evalIsCompleted = 1;
            if(board.getBoardIsDeleted() == 0) {
                result.add(MarketDto.MarketByUserResponseDto.builder()
                        .marketNo(marketRepository.findByBoard(board).get().getMarketNo())
                        .boardNo(board.getBoardNo())
                        .boardTitle(board.getBoardTitle())
                        .boardContent(board.getBoardContent())
                        .bigCategoryNo(board.getBigCategoryNo())
                        .marketIsCompleted(marketRepository.findByBoard(board).get().getMarketIsCompleted())
                        .isSeller(1)
                        .evalIsCompleted(evalIsCompleted)
                        .files(pictureRepository.findPicByBoardNo(board.getBoardNo()))
                        .build()
                );
            }
        }

        for (int i = 0; i < boardOfBuyer.size(); i++) {
            Board board = boardOfBuyer.get(i);
            Evaluation eval = evaluationRepository.findByMarketAndEvalIsSeller(marketRepository.findByBoard(board).get(), 0);
            int evalIsCompleted = 0;
            if (eval != null) evalIsCompleted = 1;

            if(board.getBoardIsDeleted() == 0) {
                result.add(MarketDto.MarketByUserResponseDto.builder()
                        .marketNo(marketRepository.findByBoard(board).get().getMarketNo())
                        .boardNo(board.getBoardNo())
                        .boardTitle(board.getBoardTitle())
                        .boardContent(board.getBoardContent())
                        .bigCategoryNo(board.getBigCategoryNo())
                        .marketIsCompleted(marketRepository.findByBoard(board).get().getMarketIsCompleted())
                        .isSeller(0)
                        .evalIsCompleted(evalIsCompleted)
                        .files(pictureRepository.findPicByBoardNo(board.getBoardNo()))
                        .build()
                );
            }
        }
        return result;
    }

    /**
     * 장터 모집 완료
     */
    @Transactional
    public String completeMarket(int marketNo, int userNo, MarketDto.MarketCompleteRequestDto dto) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[marketNo=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        User buyer = userRepository.findByUserNick(dto.getBuyerNick()).get();
        if (evaluationRepository.findByMarketAndEvalIsSeller(market, 1) != null)
            return "평가가 이미 존재합니다.";

        if (market.getBoard().getUserNo() != userNo)
            return "판매자 본인이 아닙니다.";

        if (buyer.getUserNo() == userNo)
            return "판매자 본인을 구매자로 지정할 수 없습니다.";

        // market 테이블 업데이트
        market.marketClose(1);
        market.updateMarketPerson(userRepository.findByUserNick(dto.getBuyerNick()).get());
        marketRepository.save(market);

        // evaluation 테이블에 추가
        evaluationRepository.save(Evaluation.builder()
                                .user(userRepository.findById(userNo).get())
                                .market(market)
                                .evalScore(dto.getEvalScore())
                                .evalComment(dto.getEvalComment())
                                .evalIsSeller(1)
                            .build());

        // user 신뢰도 반영
        int buyerScore = buyer.getUserScore();
        int buyerMarketScore = dto.getEvalScore();
        switch (buyerMarketScore) {
            case 1:
                buyerScore -= 3;
                break;
            case 2:
                buyerScore -= 1;
                break;
            case 3:
                break;
            case 4:
                buyerScore += 1;
                break;
            case 5:
                buyerScore += 3;
                break;
        }
        buyer.updateScoreAfterMarket(buyerScore);
        userRepository.save(buyer);

        return "success";
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

    /**
     * 장터 거래 채팅 시작
     */
    public void joinMarket(int userNo, int marketNo) throws Exception{
        Market market = marketRepository.findById(marketNo).get();
        Board board = market.getBoard();
        User seller = userRepository.findById(board.getUserNo()).get();
        User buyer = userRepository.findById(userNo).get();

        if (seller.getUserNo() == userNo)
            throw new Exception("사용자 본인과 거래를 할 수 없습니다.");

        Optional<MarketJoin> marketJoin = marketJoinRepository.findByMarketAndBuyer(market, buyer);
        if (marketJoin.isPresent())
            throw new Exception("이미 시작한 거래입니다.");

        marketJoinRepository.save(MarketJoin.builder()
                .market(market)
                .buyer(buyer)
                .build());
    }

    /**
     * 거래 참여자 목록 조회
     */
    public List<String> getBuyers(int userNo, int marketNo) throws Exception{
        List<String> buyerNick = new ArrayList<>();
        Board board = marketRepository.findById(marketNo).get().getBoard();
        int sellerNo = board.getUserNo();

        if (userNo != sellerNo)
            throw new Exception("판매자 본인이 아닙니다.");

        List<MarketJoin> marketJoins = marketJoinRepository.findByMarket(marketRepository.findById(marketNo).get());

        for (int i = 0; i < marketJoins.size(); i++) {
            User buyer = userRepository.findById(marketJoins.get(i).getBuyer().getUserNo()).get();
            buyerNick.add(buyer.getUserNick());
        }

        return buyerNick;
    }

    /**
     * 구매자 -> 판매자 평가
     */
    public String evalSellerFromBuyer(int userNo, int marketNo, MarketDto.EvalSellerFromBuyerRequestDto dto) {
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[marketNo=" + marketNo + "] 해당 게시글이 존재하지 않습니다."));
        Board board = marketRepository.findById(marketNo).get().getBoard();
        User seller = userRepository.findById(board.getUserNo()).get();

        if (evaluationRepository.findByMarketAndEvalIsSeller(market, 0) != null)
            return "평가가 이미 존재합니다.";

        if (market.getMarketPerson().getUserNo() != userNo)
            return "구매자 본인이 아닙니다.";

        // evaluation 테이블에 추가
        evaluationRepository.save(Evaluation.builder()
                .user(userRepository.findById(userNo).get())
                .market(market)
                .evalScore(dto.getEvalScore())
                .evalComment(dto.getEvalComment())
                .evalIsSeller(0)
                .build());

        // user 신뢰도 반영
        int sellerScore = seller.getUserScore();
        int sellerMarketScore = dto.getEvalScore();
        switch (sellerMarketScore) {
            case 1:
                sellerScore -= 3;
                break;
            case 2:
                sellerScore -= 1;
                break;
            case 3:
                break;
            case 4:
                sellerScore += 1;
                break;
            case 5:
                sellerScore += 3;
                break;
        }
        seller.updateScoreAfterMarket(sellerScore);
        userRepository.save(seller);

        return "success";
    }

    /**
     * 구매자, 판매자 서로의 평가 조회
     */
    public MarketDto.EvalsResponseDto getEvals(int marketNo) throws Exception{
        Market market = marketRepository.findById(marketNo).orElseThrow(() -> new IllegalAccessError("[marketNo=" + marketNo + "] 해당 마켓글이 존재하지 않습니다."));
        Evaluation evalByBuyer = evaluationRepository.findByMarketAndEvalIsSeller(market, 0);
        Evaluation evalBySeller = evaluationRepository.findByMarketAndEvalIsSeller(market, 1);
        User seller = userRepository.findById(evalBySeller.getUser().getUserNo()).get();
        User buyer = userRepository.findById(evalByBuyer.getUser().getUserNo()).get();

        if (seller == null)
            throw new Exception("판매자 사용자가 존재하지 않습니다.");
        if (buyer == null)
            throw new Exception("구매자 사용자가 존재하지 않습니다.");

        if (evalBySeller == null)
            throw new Exception("판매자의 평가가 이루어지지 않았습니다.");
        if (evalByBuyer == null)
            throw new Exception("구매자의 평가가 이루어지지 않았습니다.");

        return MarketDto.EvalsResponseDto.builder()
                .buyerUserNo(buyer.getUserNo())
                .buyerUserNick(buyer.getUserNick())
                .buyerScore(evalBySeller.getEvalScore())
                .buyerComment(evalBySeller.getEvalComment())
                .sellerUserNo(seller.getUserNo())
                .sellerUserNick(seller.getUserNick())
                .sellerScore(evalByBuyer.getEvalScore())
                .sellerComment(evalByBuyer.getEvalComment())
                .build();
    }



}
