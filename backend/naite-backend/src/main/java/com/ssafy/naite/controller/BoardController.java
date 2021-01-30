package com.ssafy.naite.controller;

import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.service.board.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RestController
@RequestMapping("/board")
@AllArgsConstructor
@Api(value = "게시판")
public class BoardController {

    private final BoardService boardService;

    /**
     * 게시글 전체 조회
     */
    @GetMapping("/list")
    @ApiOperation(value = "게시글 전체 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards() {
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoards();
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 카테고리별 조회
     */
    @GetMapping("/list/{bigCategoryNo}")
    @ApiOperation(value = "게시글 카테고리별 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoardsByCategory(@PathVariable int bigCategoryNo) {
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoardsByCategory(bigCategoryNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 제목으로 조회
     */
    @GetMapping("/detail/title/{boardTitle}")
    @ApiOperation(value = "게시글 제목으로 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoardsByTitle(@PathVariable String boardTitle) {
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoardsByTitle(boardTitle);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/detail/{boardNo}")
    @ApiOperation(value = "게시글 상세 조회")
    public ResponseEntity<BoardDto.BoardResponseDto> findBoardById(@PathVariable int boardNo) {
        BoardDto.BoardResponseDto boardResponseDto = boardService.findBoardById(boardNo);
        return new ResponseEntity<BoardDto.BoardResponseDto>(boardResponseDto, HttpStatus.OK);
    }

    /**
     * 게시글 등록
     */
    @PostMapping("/insert")
    @ApiOperation(value = "게시글 등록")
    public ResponseEntity<Integer> insertBoard(@RequestBody BoardDto.BoardSaveRequestDto boardSaveRequestDto) {
        int insertedBoardNo = boardService.insertBoard(boardSaveRequestDto);
        return new ResponseEntity<Integer>(insertedBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/update/{boardNo}")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<Integer> updateBoard(@PathVariable int boardNo, @RequestBody BoardDto.BoardUpdateRequestDto boardUpdateRequestDto) {
        int updatedBoardNo = boardService.updateBoard(boardNo, boardUpdateRequestDto);
        return new ResponseEntity<Integer>(updatedBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 삭제
     */
    @PutMapping("/delete/{boardNo}")
    @ApiOperation(value = "게시글 삭제")
    public ResponseEntity<Integer> deleteBoard(@PathVariable int boardNo) {
        int deletedBoardNo = boardService.deleteBoard(boardNo);
        return new ResponseEntity<Integer>(deletedBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 복구
     */
    @PutMapping("/restore/{boardNo}")
    @ApiOperation(value = "게시글 복구")
    public ResponseEntity<Integer> restoreBoard(@PathVariable int boardNo) {
        int restoreBoardNo = boardService.restoreBoard(boardNo);
        return new ResponseEntity<Integer>(restoreBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 좋아요
     */
    @PostMapping("/like")
    @ApiOperation(value = "게시글 좋아요")
    public ResponseEntity<Integer> addLikeToBoard(@RequestBody BoardDto.LikeRequestSaveDto likeRequestSaveDto) {
        int addedLikeBoardNo = boardService.addLikeToBoard(likeRequestSaveDto);
        return new ResponseEntity<Integer>(addedLikeBoardNo, HttpStatus.CREATED);
    }
}
