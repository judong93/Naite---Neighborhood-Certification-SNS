package com.ssafy.naite.controller;

import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.service.board.BoardService;
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
@RequestMapping("/board")
@AllArgsConstructor
@Api(value = "게시판")
public class BoardController {

    private final BoardService boardService;
    private final JwtService jwtService;

    /**
     * 게시글 전체 조회
     */
    @GetMapping("/list")
    @ApiOperation(value = "게시글 전체 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoards(HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoards(userNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 카테고리별 조회
     */
    @GetMapping("/list/{bigCategoryNo}")
    @ApiOperation(value = "게시글 카테고리별 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoardsByCategory(@PathVariable int bigCategoryNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoardsByCategory(bigCategoryNo, userNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 좋아요 높은 순서로 5개
     */
    @GetMapping("/list/top/{bigCategoryNo}")
    @ApiOperation(value = "게시글 좋아요 많은 순 카테고리별 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findTopLikedBoardsByCategory(@PathVariable int bigCategoryNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findTopLikedBoardsByCategory(bigCategoryNo, userNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 유저로 조회
     */
    @GetMapping("/list/user/{userNo}")
    @ApiOperation(value = "게시글 유저별 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoardsByUserNo(@PathVariable int userNo) {
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoardsByUserNo(userNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 제목으로 조회
     */
    @GetMapping("/list/title/{boardTitle}")
    @ApiOperation(value = "게시글 제목으로 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findAllBoardsByTitle(@PathVariable String boardTitle, HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllBoardsByTitle(boardTitle, userNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    /**
     * 게시글 상세 조회
     */
    @GetMapping("/list/detail/{boardNo}")
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
    public ResponseEntity<Integer> insertBoard(@ModelAttribute BoardDto.BoardSaveRequestDto boardSaveRequestDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        try {
            int insertedBoardNo = boardService.insertBoard(boardSaveRequestDto, userNo);
            return new ResponseEntity<Integer>(insertedBoardNo, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<Integer>(-1,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/update/{boardNo}")
    @ApiOperation(value = "게시글 수정")
    public ResponseEntity<Integer> updateBoard(@PathVariable int boardNo, @RequestBody BoardDto.BoardUpdateRequestDto boardUpdateRequestDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int updatedBoardNo = boardService.updateBoard(boardNo, boardUpdateRequestDto, userNo);
        if(updatedBoardNo < 0) {
            return new ResponseEntity<Integer>(updatedBoardNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(updatedBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 삭제
     */
    @PutMapping("/delete/{boardNo}")
    @ApiOperation(value = "게시글 삭제")
    public ResponseEntity<Integer> deleteBoard(@PathVariable int boardNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int deletedBoardNo = boardService.deleteBoard(boardNo, userNo);
        if(deletedBoardNo < 0) {
            return new ResponseEntity<Integer>(deletedBoardNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(deletedBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 복구
     */
    @PutMapping("/restore/{boardNo}")
    @ApiOperation(value = "게시글 복구")
    public ResponseEntity<Integer> restoreBoard(@PathVariable int boardNo, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int restoreBoardNo = boardService.restoreBoard(boardNo, userNo);
        if(restoreBoardNo < 0) {
            return new ResponseEntity<Integer>(restoreBoardNo, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<Integer>(restoreBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 좋아요 등록
     */
    @PostMapping("/like")
    @ApiOperation(value = "게시글 좋아요 등록")
    public ResponseEntity<Integer> addLikeToBoard(@RequestBody BoardDto.LikeRequestSaveDto likeRequestSaveDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int addedLikeBoardNo = boardService.addLikeToBoard(likeRequestSaveDto, userNo);
        return new ResponseEntity<Integer>(addedLikeBoardNo, HttpStatus.CREATED);
    }

    /**
     * 게시글 좋아요 삭제
     */
    @PostMapping("/dislike")
    @ApiOperation(value = "게시글 좋아요 삭제")
    public ResponseEntity<Integer> deleteLikeToBoard(@RequestBody BoardDto.LikeRequestSaveDto likeRequestSaveDto, HttpServletRequest req) {
        int userNo = getUserNo(req);
        int addedLikeBoardNo = boardService.deleteLikeToBoard(likeRequestSaveDto, userNo);
        return new ResponseEntity<Integer>(addedLikeBoardNo, HttpStatus.CREATED);
    }

    /**
     * 유저별 좋아요한 게시글 전체 조회
     */
    @GetMapping("/like")
    @ApiOperation(value = "유저별 좋아요 누른 게시글 전체 조회")
    public ResponseEntity<List<BoardDto.BoardResponseDto>> findLikesByBoardNo(HttpServletRequest req) {
        int userNo = getUserNo(req);
        List<BoardDto.BoardResponseDto> boardResponseDtoList = boardService.findAllLikesByUserNo(userNo);
        return new ResponseEntity<List<BoardDto.BoardResponseDto>>(boardResponseDtoList, HttpStatus.OK);
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
