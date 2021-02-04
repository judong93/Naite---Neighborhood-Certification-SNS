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

    @Transactional
    public List<CommentGetResponseDto> getComments(String userToken, int boardId) throws Exception{
        List<Comment> list = commentRepository.getCommentsByBoardId(new Board(boardId));
        List<CommentGetResponseDto> returnList = new ArrayList<>();

        jwtService.checkValid(userToken);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jwtService.get(userToken).get("user"));
        User user = objectMapper.readValue(json, User.class);

        for (int i = 0; i < list.size(); i++) {
            Comment c = list.get(i);
            boolean userOwn = false;
            if (c.getUser().getUserNo() == user.getUserNo()) {
                userOwn = true;
            }
            CommentGetResponseDto dto = CommentGetResponseDto.builder()
                    .content(c.getCommentContent())
                    .createdAt(c.getCommentCreatedAt())
                    .updatedAt(c.getCommentUpdatedAt())
                    .userNick(c.getUser().getUserNick())
                    .parentId(c.getCommentParentId())
                    .userOwn(userOwn)
                    .build();
            returnList.add(dto);
        }

        return returnList;
    }

    public void postComment(String userToken, CommentPostRequestDto commentPostRequestDto) throws Exception {
        jwtService.checkValid(userToken);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jwtService.get(userToken).get("user"));
        User user = objectMapper.readValue(json, User.class);

        Comment comment = Comment.builder()
                .commentContent(commentPostRequestDto.getContent())
                .commentParentId(commentPostRequestDto.getParentId())
                .commentReportCnt(0)
                .commentIsDeleted(Byte.parseByte("0"))
                .board(new Board(commentPostRequestDto.getBoardId()))
                .user(new User(user.getUserNo()))
                .commentCreatedAt(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                .build();

        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Exception("댓글 등록 실패");
        }
    }

    @Transactional
    public void putComment(String userToken, int commentId, CommentPutRequestDto commentPutRequestDto) throws Exception{
        jwtService.checkValid(userToken);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jwtService.get(userToken).get("user"));
        User user = objectMapper.readValue(json, User.class);

        Comment comment = commentRepository.findById(commentId).get();
        if (comment.getUser().getUserNo() != user.getUserNo()) {
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
    public void deleteComment(String userToken, int commentId) throws Exception{
        jwtService.checkValid(userToken);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(jwtService.get(userToken).get("user"));
        User user = objectMapper.readValue(json, User.class);

        Comment comment = commentRepository.findById(commentId).get();
        if (comment.getUser().getUserNo() != user.getUserNo()) {
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
