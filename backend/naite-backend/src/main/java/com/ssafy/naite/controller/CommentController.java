package com.ssafy.naite.controller;

import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.dto.comment.CommentGetResponseDto;
import com.ssafy.naite.dto.comment.CommentPostRequestDto;
import com.ssafy.naite.dto.comment.CommentPutRequestDto;
import com.ssafy.naite.dto.user.UserGetProfileResponseDto;
import com.ssafy.naite.dto.util.Response;
import com.ssafy.naite.service.comment.CommentService;
import com.ssafy.naite.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final UserService userService;

    @ApiOperation(value = "댓글 조회")
    @GetMapping("/{id}")
    public Response getComment(@ApiParam(value = "유저 토큰") @RequestHeader String userToken, @ApiParam(value = "게시글 인덱스") @PathVariable("id") int boardId) {
        List<CommentGetResponseDto> list;
        try {
            list = commentService.getComments(userToken, boardId);
        } catch (Exception e) {
            return new Response("error", "댓글 조회 실패", null);
        }
        return new Response("success", "댓글 조회 성공", list);
    }


    @ApiOperation(value = "댓글 등록")
    @PostMapping
    public Response postComment(@ApiParam(value = "유저 토큰") @RequestHeader String userToken, @RequestBody CommentPostRequestDto commentPostRequestDto) {
        try {
            commentService.postComment(userToken, commentPostRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 등록 성공", null);
    }


    @ApiOperation(value = "댓글 수정")
    @PutMapping("/{id}")
    public Response putComment(@ApiParam(value = "유저 토큰") @RequestHeader String userToken, @ApiParam(value = "수정할 댓글 인덱스") @PathVariable("id") int commentId, @RequestBody CommentPutRequestDto commentPutRequestDto) {
        try {
            commentService.putComment(userToken, commentId, commentPutRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 수정 성공", null);
    }


    @ApiOperation(value = "댓글 삭제")
    @DeleteMapping("/{id}")
    public Response deleteComment(@ApiParam(value = "유저 토큰") @RequestHeader String userToken, @ApiParam(value = "삭제할 댓글 인덱스") @PathVariable("id") int commentId) {
        try {
            commentService.deleteComment(userToken, commentId);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 삭제 성공", null);
    }

    @ApiOperation(value = "유저별 댓글 쓴 게시글 조회")
    @GetMapping("/user")
    public Response getBoardByUserComment(@ApiParam(value = "유저 토큰") @RequestHeader("auth-token") String userToken,
                                          @ApiParam(value = "유저 아이디") @RequestHeader("userId") String userId) {
        try {
            User user = userService.findByUserId(userId);
            List<BoardDto.BoardResponseDto> result = commentService.getBoardByUserComment(user);
            return new Response("success", "댓글 쓴 게시글 조회 성공", result);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
    }
}
