package com.ssafy.naite.controller;

import com.ssafy.naite.dto.comment.CommentGetResponseDto;
import com.ssafy.naite.dto.comment.CommentPostRequestDto;
import com.ssafy.naite.dto.comment.CommentPutRequestDto;
import com.ssafy.naite.dto.util.Response;
import com.ssafy.naite.service.comment.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@AllArgsConstructor
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    // TODO: 댓글 조회
    @GetMapping("/{id}")
    public Response getComment(@PathVariable("id") int boardId) {
        List<CommentGetResponseDto> list;
        try {
            list = commentService.getComments(boardId);
        } catch (Exception e) {
            return new Response("error", "댓글 조회 실패", null);
        }
        return new Response("success", "댓글 조회 성공", list);
    }

    // 댓글 등록
    @PostMapping
    public Response postComment(@RequestBody CommentPostRequestDto commentPostRequestDto) {
        try {
            commentService.postComment(commentPostRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 등록 성공", null);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public Response putComment(@PathVariable("id") int commentId, @RequestBody CommentPutRequestDto commentPutRequestDto) {
        try {
            commentService.putComment(commentId, commentPutRequestDto);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 수정 성공", null);
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public Response deleteComment(@PathVariable("id") int commentId) {
        try {
            commentService.deleteComment(commentId);
        } catch (Exception e) {
            return new Response("error", e.getMessage(), null);
        }
        return new Response("success", "댓글 삭제 성공", null);
    }

}
