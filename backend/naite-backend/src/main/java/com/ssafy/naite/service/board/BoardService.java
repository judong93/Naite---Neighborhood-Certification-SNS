package com.ssafy.naite.service.board;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.like.LikePK;
import com.ssafy.naite.domain.like.LikeRepository;
import com.ssafy.naite.dto.board.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;

    /**
     * 게시글 전체 조회
     */
    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponseDto> findAllBoards() {
        return boardRepository.findAll().stream().filter(board -> board.getBoardIsDeleted() == 0).map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 게시글 카테고리별 조회
     */
    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponseDto> findAllBoardsByCategory(int bigCategoryNo) {
        return boardRepository.findAll().stream().filter(board -> board.getBoardIsDeleted() == 0).filter(board -> board.getBigCategoryNo() == bigCategoryNo).map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 게시글 제목으로 조회
     */
    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponseDto> findAllBoardsByTitle(String boardTitle) {
        return boardRepository.findAll().stream().filter(board -> board.getBoardIsDeleted() == 0).filter(board -> board.getBoardTitle().contains(boardTitle)).map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 게시글 상세 조회
     */
    @Transactional(readOnly = true)
    public BoardDto.BoardResponseDto findBoardById(int boardNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalAccessError("[board_no=" + boardNo + "] 해당 게시글이 존재하지 않습니다."));

        return new BoardDto.BoardResponseDto(board);
    }

    /**
     * 게시글 등록
     */
    @Transactional
    public int insertBoard(BoardDto.BoardSaveRequestDto boardSaveRequestDto) {
        return boardRepository.save(boardSaveRequestDto.toEntity()).getBoardNo();
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public int updateBoard(int boardNo, BoardDto.BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalAccessError("[board_no=" + boardNo + "] 해당 게시글이 존재하지 않습니다."));
        board.update(boardUpdateRequestDto.getBoardTitle(), boardUpdateRequestDto.getBoardContent(),
                boardUpdateRequestDto.getBoardPic(), boardUpdateRequestDto.getUnknownFlag(), boardUpdateRequestDto.getOpenFlag());

        return boardNo;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public int deleteBoard(int boardNo) {
        Board board = boardRepository.findById(boardNo)
                .orElseThrow(() -> new IllegalAccessError("[board_no=" + boardNo + "] 해당 게시글이 존재하지 않습니다."));
        board.delete(1);
        boardRepository.save(board);

        return boardNo;
    }

    /**
     * 게시글 복구
     */
    @Transactional
    public int restoreBoard(int board_no) {
        Board board = boardRepository.findById(board_no)
                .orElseThrow(() -> new IllegalAccessError("[board_no=" + board_no + "] 해당 게시글이 존재하지 않습니다."));
        board.delete(0);
        boardRepository.save(board);
        return board_no;
    }

    /**
     * 좋아요 추가
     */
    @Transactional
    public int addLikeToBoard(BoardDto.LikeRequestSaveDto likeRequestSaveDto) {
        Board board = boardRepository.findById(likeRequestSaveDto.toEntity().getBoardNo())
                .orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
        LikePK likePK = new LikePK();
        likePK.setUserNo(likeRequestSaveDto.getUserNo());
        likePK.setBoardNo(likeRequestSaveDto.getBoardNo());
        if(!likeRepository.findById(likePK).isPresent()) {
            board.like(true);
        }
        return likeRepository.save(likeRequestSaveDto.toEntity()).getBoardNo();
    }

    /**
     * 좋아요 삭제
     */
    @Transactional
    public int deleteLikeToBoard(BoardDto.LikeRequestSaveDto likeRequestSaveDto) {
        Board board = boardRepository.findById(likeRequestSaveDto.toEntity().getBoardNo())
                .orElseThrow(() -> new IllegalAccessError("해당 게시글이 존재하지 않습니다."));
        board.like(false);
        likeRepository.delete(likeRequestSaveDto.toEntity());
        return board.getBoardNo();
    }

    /**
     * 해당 게시글 좋아요 누른 유저 조회
     */
    @Transactional(readOnly = true)
    public List<Integer> findAllLikesByBoardNo(int boardNo) {
        List<BoardDto.LikeResponseDto> likeResponseDtoList = likeRepository.findAll().stream().filter(boardLike -> boardLike.getBoardNo() == boardNo).map(BoardDto.LikeResponseDto::new).collect(Collectors.toList());
        List<Integer> likeUserList = new ArrayList<Integer>();
        for(BoardDto.LikeResponseDto likeResponseDto : likeResponseDtoList) {
            likeUserList.add(likeResponseDto.getUserNo());
        }
        return likeUserList;
    }
}
