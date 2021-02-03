package com.ssafy.naite.domain.market;

import com.ssafy.naite.domain.board.Board;
import com.ssafy.naite.domain.comment.Comment;
import com.ssafy.naite.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarketRepository extends JpaRepository<Market, Integer> {
    @Query("SELECT b FROM Board b, Market m WHERE b.boardNo = m.board.boardNo and b.userNo = :userNo")
    public List<Board> getMarketsByUserNo(int userNo);
}
