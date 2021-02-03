package com.ssafy.naite.domain.comment;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
    List<Comment> findByUser(User user);
    List<Comment> findByCommentParentId(Integer id);

    @Query("SELECT c FROM Comment AS c WHERE c.board = :board ORDER BY " +
            "(CASE WHEN c.commentParentId = 0 THEN c.commentNo ELSE c.commentParentId END)")
    public List<Comment> getCommentsByBoardId(@Param("board") Board board);
}
