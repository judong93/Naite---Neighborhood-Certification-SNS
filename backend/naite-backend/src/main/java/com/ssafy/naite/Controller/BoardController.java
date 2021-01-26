package com.ssafy.naite.Controller;

import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.service.Board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/board")
@AllArgsConstructor
@Api(value = "게시판")
public class BoardController {

    private final BoardService boardService;

    /** 게시글 전체 조회 */
    @GetMapping("/list")
    @ApiOperation(value = "게시글 전체 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards(){
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoards();
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /** 게시글 상세 조회 */
    @GetMapping("/lookup/{board_no}")
    @ApiOperation(value = "게시글 상세 조회")
    public ResponseEntity<BoardDto.BoardResponseDto> getBoardById(@PathVariable int board_no) {
        BoardDto.BoardResponseDto boardResponseDto= boardService.findById(board_no);
        return new ResponseEntity<BoardDto.BoardResponseDto>(boardResponseDto, HttpStatus.OK);
    }

    /** 게시글 등록 */
    @PostMapping("/insert")
    @ApiOperation(value = "게시글 등록")
    public ResponseEntity<Integer> insertBoard(@RequestBody BoardDto.BoardSaveRequestDto boardSaveRequestDto) {
        int insertedBoardNo = boardService.insertBoard(boardSaveRequestDto);
        return new ResponseEntity<Integer>(insertedBoardNo, HttpStatus.CREATED);
    }

    /** 게시글 수정 */
    @PutMapping("/update/{board_no}")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<Integer> updateBoard(@PathVariable int board_no, @RequestBody BoardDto.BoardUpdateRequestDto boardUpdateRequestDto) {
        int updatedBoardNo = boardService.updateBoard(board_no, boardUpdateRequestDto);
        return new ResponseEntity<Integer>(updatedBoardNo, HttpStatus.CREATED);
    }

    /** 게시글 삭제 */
    @PutMapping("/delete/{board_no}")
    @ApiOperation(value = "게시글 삭제")
    public ResponseEntity<Integer> deleteBoard(@PathVariable int board_no) {
        int deletedBoardNo = boardService.deleteBoard(board_no);
        return new ResponseEntity<Integer>(deletedBoardNo, HttpStatus.CREATED);
    }

    /** 게시글 복구 */
    @PutMapping("/restore/{board_no}")
    @ApiOperation(value = "게시글 복구")
    public ResponseEntity<Integer> restoreBoard(@PathVariable int board_no) {
        int restoreBoardNo = boardService.restoreBoard(board_no);
        return new ResponseEntity<Integer>(restoreBoardNo, HttpStatus.CREATED);
    }
}
