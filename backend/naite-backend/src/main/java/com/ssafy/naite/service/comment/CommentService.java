package com.ssafy.naite.service.comment;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.comment.CommentRepository;
import com.ssafy.naite.domain.user.User;
import com.ssafy.naite.dto.comment.CommentGetResponseDto;
import com.ssafy.naite.dto.comment.CommentPostRequestDto;
import com.ssafy.naite.dto.comment.CommentPutRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public List<CommentGetResponseDto> getComments(int boardId) {
        List<Comment> list = commentRepository.getCommentsByBoardId(Board.builder().boardNo(boardId).build());
        List<CommentGetResponseDto> returnList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            Comment c = list.get(i);
            CommentGetResponseDto dto = CommentGetResponseDto.builder()
                    .content(c.getCommentContent())
                    .createdAt(c.getCommentCreatedAt())
                    .updatedAt(c.getCommentUpdatedAt())
                    .userNick(c.getUser().getUserNick())
                    .parentId(c.getCommentParentId())
                    .build();
            returnList.add(dto);
        }

        return returnList;
    }

//    @Transactional
    public void postComment(CommentPostRequestDto commentPostRequestDto) throws Exception {
        // TODO[수정]: 토큰값 해석해서 userNo 값 가져와야함
        Comment comment = Comment.builder()
                .commentContent(commentPostRequestDto.getContent())
                .commentParentId(commentPostRequestDto.getParentId())
                .commentReportCnt(0)
                .commentIsDeleted(Byte.parseByte("0"))
                .board(Board.builder().boardNo(commentPostRequestDto.getBoardId()).build())
                .user(User.builder().userNo(1).build())
                .commentCreatedAt(new Timestamp(System.currentTimeMillis() + (1000 * 60 * 60 * 9)))
                .build();

        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Exception("댓글 등록 실패");
        }
    }

    @Transactional
    public void putComment(int commentId, CommentPutRequestDto commentPutRequestDto) throws Exception{
        Comment comment = commentRepository.findById(commentId).get();
        comment.updateTime();
        comment.updateContent(commentPutRequestDto.getCommentContent());
        try {
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Exception("댓글 수정 실패");
        }
    }

    @Transactional
    public void deleteComment(int commentId) throws Exception{
        try {
            Comment comment = commentRepository.findById(commentId).get();
            comment.updateDelete();
            commentRepository.save(comment);
        } catch (Exception e) {
            throw new Exception("댓글 삭제 실패");
        }
    }
}
