package com.ssafy.naite.controller;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.dto.comment.CommentGetResponseDto;
import com.ssafy.naite.dto.comment.CommentPostRequestDto;
import com.ssafy.naite.dto.comment.CommentPutRequestDto;
import com.ssafy.naite.dto.user.UserGetProfileResponseDto;
import com.ssafy.naite.dto.util.Response;
import com.ssafy.naite.service.comment.CommentService;
import com.ssafy.naite.service.user.JwtService;
import com.ssafy.naite.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;
    private final JwtService jwtService;

    @ApiOperation(value = "댓글 조회")
    @GetMapping("/{id}")
    public Response getComment(@ApiParam(value = "게시글 인덱스") @PathVariable("id") int boardId, HttpServletRequest req) {
        List<CommentGetResponseDto> list;
        try {
            int userNo = getUserNo(req);
            list = commentService.getComments(userNo, boardId);
        } catch (Exception e) {
            return new Response("error", "댓글 조회 실패", null);
        }
        return new Response("success", "댓글 조회 성공", list);
    }


    @ApiOperation(value = "댓글 등록")
    @PostMapping
    public Response postComment(@RequestBody CommentPostRequestDto commentPostRequestDto, HttpServletRequest req) {
        try {
            int userNo = getUserNo(req);
            commentService.postComment(userNo, commentPostRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 등록 성공", null);
    }


    @ApiOperation(value = "댓글 수정")
    @PutMapping("/{id}")
    public Response putComment(@ApiParam(value = "수정할 댓글 인덱스") @PathVariable("id") int commentId, @RequestBody CommentPutRequestDto commentPutRequestDto, HttpServletRequest req) {
        try {
            int userNo = getUserNo(req);
            commentService.putComment(userNo, commentId, commentPutRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 수정 성공", null);
    }


    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{id}")
    public Response deleteComment(@ApiParam(value = "삭제할 댓글 인덱스") @PathVariable("id") int commentId, HttpServletRequest req) {
        try {
            int userNo = getUserNo(req);
            commentService.deleteComment(userNo, commentId);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 삭제 성공", null);
    }

    @ApiOperation(value = "유저별 댓글 쓴 게시글 조회")
    @GetMapping("/user")
    public Response getBoardByUserComment(HttpServletRequest req) {
        try {
            int userNo = getUserNo(req);
            User user = userService.findByUserNo(userNo);
            List<BoardDto.BoardResponseDto> result = commentService.getBoardByUserComment(user);
            return new Response("success", "댓글 쓴 게시글 조회 성공", result);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }

    public int getUserNo(HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.putAll(jwtService.get(req.getHeader("auth-token")));
        return (int) ((Map<String, Object>) resultMap.get("user")).get("userNo");
    }
}
