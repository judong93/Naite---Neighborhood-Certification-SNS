package com.ssafy.naite.domain.board;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int board_no;

    @Column(nullable = false)
    private int user_no;

    @Column(nullable = false)
    private int big_category_no;

    @Lob
    @Column(nullable = false)
    private String board_title;

    @Lob
    @Column(nullable = false)
    private String board_content;

    private String board_pic;

    @Column(updatable = false)
    private LocalDateTime board_created_at;

    @LastModifiedDate
    private LocalDateTime board_updated_at;

    private int board_like_cnt;

    @Column(nullable = false)
    private int unknown_flag;

    private int board_report_cnt;

    @Column(nullable = false)
    private int open_flag;

    @Column(nullable = false)
    private int board_is_deleted;

    @Builder
    public Board(int user_no, int big_category_no, String board_title, String board_content, String board_pic,
                 LocalDateTime board_created_at, LocalDateTime board_updated_at, int board_like_cnt,
                 int unknown_flag, int board_report_cnt, int open_flag, int board_is_deleted) {
        this.user_no = user_no;
        this.big_category_no = big_category_no;
        this.board_title = board_title;
        this.board_content = board_content;
        this.board_pic = board_pic;
        this.board_created_at = board_created_at;
        this.board_updated_at = board_updated_at;
        this.board_like_cnt = board_like_cnt;
        this.unknown_flag = unknown_flag;
        this.board_report_cnt = board_report_cnt;
        this.open_flag = open_flag;
        this.board_is_deleted = board_is_deleted;
    }

    public void update(String board_title, String board_content, String board_pic, int unknown_flag, int open_flag) {
        this.board_title = board_title;
        this.board_content = board_content;
        this.board_pic = board_pic;
        this.board_updated_at = LocalDateTime.now();
        this.unknown_flag = unknown_flag;
        this.open_flag = open_flag;
    }

    public void delete(int board_is_deleted) {
        this.board_updated_at = LocalDateTime.now();
        this.board_is_deleted = board_is_deleted;
    }
}
