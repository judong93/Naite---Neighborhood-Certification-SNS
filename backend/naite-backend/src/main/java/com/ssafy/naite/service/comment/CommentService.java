package com.ssafy.naite.service.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.dto.comment.CommentGetResponseDto;
import com.ssafy.naite.dto.comment.CommentPostRequestDto;
import com.ssafy.naite.dto.comment.CommentPutRequestDto;
import com.ssafy.naite.service.user.JwtService;
import com.ssafy.naite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final JwtService jwtService;
    private final UserService userService;

    @Transactional
    public List<CommentGetResponseDto> getComments(int userNo, int boardId) throws Exception{
        List<Comment> list = commentRepository.getCommentsByBoardId(new Board(boardId));
        List<CommentGetResponseDto> returnList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Comment c = list.get(i);
            boolean userOwn = false;
            if (c.getUser().getUserNo() == userNo) {
                userOwn = true;
            }
            CommentGetResponseDto dto = CommentGetResponseDto.builder()
                    .commentNo(c.getCommentNo())
                    .content(c.getCommentContent())
                    .createdAt(c.getCommentCreatedAt())
                    .updatedAt(c.getCommentUpdatedAt())
                    .userNick(c.getUser().getUserNick())
                    .parentId(c.getCommentParentId())
                    .userOwn(userOwn)
                    .isUnknown(c.getCommentIsUnknown())
                    .isDeleted(c.getCommentIsDeleted())
                    .build();
            returnList.add(dto);
        }



        return returnList;
    }

    public CommentGetResponseDto postComment(int userNo, CommentPostRequestDto commentPostRequestDto) throws Exception {
        Comment comment = Comment.builder()
                .commentContent(commentPostRequestDto.getContent())
                .commentParentId(commentPostRequestDto.getParentId())
                .commentReportCnt(0)
                .commentIsDeleted(Byte.parseByte("0"))
                .board(new Board(commentPostRequestDto.getBoardId()))
                .user(new User(userNo))
                .commentCreatedAt(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                .commentIsUnknown(Byte.valueOf(String.valueOf(commentPostRequestDto.getIsUnknown())))
                .build();

        try {
            Comment newComment = commentRepository.save(comment);
            boolean userOwn = false;
            if (userNo == newComment.getUser().getUserNo()) userOwn = true;
            CommentGetResponseDto dto = CommentGetResponseDto.builder()
                    .commentNo(newComment.getCommentNo())
                    .userNick(userService.findByUserNo(newComment.getUser().getUserNo()).getUserNick())
                    .createdAt(newComment.getCommentCreatedAt())
                    .updatedAt(newComment.getCommentUpdatedAt())
                    .content(newComment.getCommentContent())
                    .parentId(newComment.getCommentParentId())
                    .userOwn(userOwn)
                    .isUnknown(newComment.getCommentIsUnknown())
                    .isDeleted(newComment.getCommentIsDeleted())
                    .build();
            return dto;
        } catch (Exception e) {
            throw new Exception("댓글 작성 실패");
        }
    }

    @Transactional
    public void putComment(int userNo, int commentId, CommentPutRequestDto commentPutRequestDto) throws Exception{
        Comment comment = commentRepository.findById(commentId).get();
        if (comment.getUser().getUserNo() != userNo) {
            throw new Exception("댓글을 쓴 사용자가 아닙니다.");
        }
        comment.updateTime();
        comment.updateContent(commentPutRequestDto.getCommentContent());

        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Exception("댓글 수정 실패");
        }
    }

    @Transactional
    public void deleteComment(int userNo, int commentId) throws Exception{
        Comment comment = commentRepository.findById(commentId).get();
        if (comment.getUser().getUserNo() != userNo) {
            throw new Exception("댓글을 쓴 사용자가 아닙니다.");
        }

        try {
            comment.updateDelete();
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Exception("댓글 삭제 실패");
        }
    }

    public List<BoardDto.BoardResponseDto> getBoardByUserComment(User user) {
        List<Board> boards = commentRepository.findByUserComment(user);
        List<BoardDto.BoardResponseDto> result = new ArrayList<>();

        for (int i = 0; i < boards.size(); i++) {
            if (boards.get(i).getBoardIsDeleted() == 1) continue;
            Board b = boardRepository.findById(boards.get(i).getBoardNo()).get();
            result.add(new BoardDto.BoardResponseDto(b));
        }

        return result;
    }
}
