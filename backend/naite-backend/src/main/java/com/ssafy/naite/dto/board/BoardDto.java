package com.ssafy.naite.dto.board;

import com.ssafy.naite.domain.board.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

public class BoardDto {

    /** insert board dto */
    @Getter
    @NoArgsConstructor
    public static class BoardSaveRequestDto {
        private int user_no;
        private int big_category_no;
        private String board_title;
        private String board_content;
        private String board_pic;
        private int unknown_flag;
        private int open_flag;

        @Builder
        public BoardSaveRequestDto(int user_no, int big_category_no, String board_title, String board_content,
                                   String board_pic, int unknown_flag, int open_flag) {
            this.user_no = user_no;
            this.big_category_no = big_category_no;
            this.board_title = board_title;
            this.board_content = board_content;
            this.board_pic = board_pic;
            this.unknown_flag = unknown_flag;
            this.open_flag = open_flag;
        }

        public Board toEntity() {
            return Board.builder()
                    .user_no(user_no)
                    .big_category_no(big_category_no)
                    .board_title(board_title)
                    .board_content(board_content)
                    .board_pic(board_pic)
                    .board_created_at(LocalDateTime.now())
                    .board_updated_at(LocalDateTime.now())
                    .unknown_flag(unknown_flag)
                    .open_flag(open_flag)
                    .build();
        }
    }

    /** update board dto */
    @Getter
    @NoArgsConstructor
    public static class BoardUpdateRequestDto {
        private String board_title;
        private String board_content;
        private String board_pic;
        private int unknown_flag;
        private int open_flag;

        @Builder
        public BoardUpdateRequestDto(String board_title, String board_content, String board_pic,
                                   int unknown_flag, int open_flag) {
            this.board_title = board_title;
            this.board_content = board_content;
            this.board_pic = board_pic;
            this.unknown_flag = unknown_flag;
            this.open_flag = open_flag;
        }
    }

    /** select board dto */
    @Getter
    @Setter
    public static class BoardResponseDto {

        private int board_no;
        private int user_no;
        private int big_category_no;
        private String board_title;
        private String board_content;
        private LocalDateTime board_created_at;
        private LocalDateTime board_updated_at;
        private int board_like_cnt;
        private int unknown_flag;
        private int board_report_cnt;
        private int open_flag;
        private int board_is_deleted;

        public BoardResponseDto(Board board) {
            this.board_no = board.getBoard_no();
            this.user_no = board.getUser_no();
            this.big_category_no = board.getBig_category_no();
            this.board_title = board.getBoard_title();
            this.board_content = board.getBoard_content();
            this.board_created_at = board.getBoard_created_at();
            this.board_updated_at = board.getBoard_updated_at();
            this.board_like_cnt = board.getBoard_like_cnt();
            this.unknown_flag = board.getUnknown_flag();
            this.board_report_cnt = board.getBoard_report_cnt();
            this.open_flag = board.getOpen_flag();
            this.board_is_deleted = board.getBoard_is_deleted();
        }
    }
}
