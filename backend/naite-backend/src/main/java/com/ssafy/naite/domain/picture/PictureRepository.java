package com.ssafy.naite.domain.picture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Integer> {
    @Query("SELECT boardPic FROM Picture WHERE boardNo = :boardNo")
    List<String> findPicByBoardNo(int boardNo);
}
