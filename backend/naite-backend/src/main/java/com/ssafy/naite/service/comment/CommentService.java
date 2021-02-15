package com.ssafy.naite.service.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.board.BoardRepository;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.market.MarketRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.board.BoardDto;
import com.ssafy.naite.dto.comment.CommentGetResponseDto;
import com.ssafy.naite.dto.comment.CommentPostRequestDto;
import com.ssafy.naite.dto.comment.CommentPutRequestDto;
import com.ssafy.naite.service.user.JwtService;
import com.ssafy.naite.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.JsonSerializer;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private final MarketRepository marketRepository;
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

            String createdAt = "";
            String updatedAt = "";
            if (c.getCommentCreatedAt().plusHours(1).isAfter(LocalDateTime.now())) {
                createdAt = "방금 전";
            }
            else if (c.getCommentCreatedAt().plusDays(1).isAfter(LocalDateTime.now())) {
                int subHour = LocalDateTime.now().getHour() - c.getCommentCreatedAt().getHour();
                if (subHour < 0) subHour += 24;
                createdAt = subHour + "시간 전";
            }
            else {
                createdAt = c.getCommentCreatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)")).toString();
            }

            if (c.getCommentUpdatedAt() != null) updatedAt = c.getCommentUpdatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)")).toString();

            CommentGetResponseDto dto = new CommentGetResponseDto(
                    c.getCommentNo(),
                    c.getUser().getUserNick(),
                    c.getUser().getUserPic(),
                    createdAt,
                    updatedAt,
                    c.getCommentContent(),
                    c.getCommentParentId(),
                    userOwn,
                    c.getCommentIsUnknown(),
                    c.getCommentIsDeleted()
            );

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
                .commentCreatedAt(LocalDateTime.now())
                .commentIsUnknown(Byte.valueOf(String.valueOf(commentPostRequestDto.getIsUnknown())))
                .build();

        try {
            Comment newComment = commentRepository.save(comment);
            boolean userOwn = false;
            if (userNo == newComment.getUser().getUserNo()) userOwn = true;

            String createdAt = "";
            String updatedAt = "";
            if (newComment.getCommentCreatedAt().plusHours(1).isAfter(LocalDateTime.now())) {
                createdAt = "방금 전";
            }
            else if (newComment.getCommentCreatedAt().plusDays(1).isAfter(LocalDateTime.now())) {
                int subHour = LocalDateTime.now().getHour() - newComment.getCommentCreatedAt().getHour();
                if(subHour < 0) subHour += 24;
                createdAt = subHour + "시간 전";
            }
            else {
                createdAt = newComment.getCommentCreatedAt().plusHours(9).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm (E)")).toString();
            }

            CommentGetResponseDto dto = new CommentGetResponseDto(
                    newComment.getCommentNo(),
                    userService.findByUserNo(newComment.getUser().getUserNo()).getUserNick(),
                    userService.findByUserNo(newComment.getUser().getUserNo()).getUserPic(),
                    createdAt,
                    updatedAt,
                    newComment.getCommentContent(),
                    newComment.getCommentParentId(),
                    userOwn,
                    newComment.getCommentIsUnknown(),
                    newComment.getCommentIsDeleted()
            );

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
            int marketNo = -1;
            if (b.getBigCategoryNo() == 5) marketNo = marketRepository.findByBoard(b).get().getMarketNo();
            result.add(new BoardDto.BoardResponseDto(b));
        }

        return result;
    }
}
