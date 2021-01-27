package com.ssafy.naite.service.board;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.dto.board.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /** 게시글 전체 조회 */
    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponseDto> findAllBoards() {
        return boardRepository.findAll().stream().filter(board->board.getBoard_is_deleted() == 0).map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    /** 게시글 카테고리별 조회 */
    @Transactional(readOnly = true)
    public List<BoardDto.BoardResponseDto> findAllBoardsByCategory(int big_category_no) {
        return boardRepository.findAll().stream().filter(board->board.getBig_category_no() == big_category_no).map(BoardDto.BoardResponseDto::new).collect(Collectors.toList());
    }

    /** 게시글 상세 조회 */
    @Transactional(readOnly = true)
    public BoardDto.BoardResponseDto findBoardById(int board_no) {
        Board board = boardRepository.findById(board_no)
                                    .orElseThrow(()-> new IllegalAccessError("[board_no=" + board_no + "] 해당 게시글이 존재하지 않습니다."));

        return new BoardDto.BoardResponseDto(board);
    }

    /** 게시글 등록 */
    @Transactional
    public int insertBoard(BoardDto.BoardSaveRequestDto boardSaveRequestDto) {
        return boardRepository.save(boardSaveRequestDto.toEntity()).getBoard_no();
    }

    /** 게시글 수정 */
    @Transactional
    public int updateBoard(int board_no, BoardDto.BoardUpdateRequestDto boardUpdateRequestDto) {
        Board board = boardRepository.findById(board_no)
                                    .orElseThrow(()-> new IllegalAccessError("[board_no=" + board_no + "] 해당 게시글이 존재하지 않습니다."));
        board.update(boardUpdateRequestDto.getBoard_title(),boardUpdateRequestDto.getBoard_content(),
                boardUpdateRequestDto.getBoard_pic(), boardUpdateRequestDto.getUnknown_flag(), boardUpdateRequestDto.getOpen_flag());

        return board_no;
    }

    /** 게시글 삭제 */
    public int deleteBoard(int board_no) {
        Board board = boardRepository.findById(board_no)
                                    .orElseThrow(()-> new IllegalAccessError("[board_no=" + board_no + "] 해당 게시글이 존재하지 않습니다."));
        board.delete(1);
        boardRepository.save(board);

        return board_no;
    }

    /** 게시글 복구 */
    public int restoreBoard(int board_no) {
        Board board = boardRepository.findById(board_no)
                                    .orElseThrow(()-> new IllegalAccessError("[board_no=" + board_no + "] 해당 게시글이 존재하지 않습니다."));
        board.delete(0);
        boardRepository.save(board);
        return board_no;
    }
}
