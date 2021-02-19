package com.ssafy.naite.dto.picture;

import com.ssafy.naite.domain.picture.Picture;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class PictureDto {

    /**
     * insert picture dto
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class PictureSaveRequestDto {
        private int picNo;
        private int boardNo;
        private String boardPic;

        @Builder
        public PictureSaveRequestDto(int picNo, int boardNo, String boardPic) {
            this.picNo = picNo;
            this.boardNo = boardNo;
            this.boardPic = boardPic;
        }

        public Picture toEntity() {
            return Picture.builder()
                    .picNo(picNo)
                    .boardNo(boardNo)
                    .boardPic(boardPic)
                    .build();
        }
    }

    /**
     * update picture dto
     */
    @Getter
    @NoArgsConstructor
    public static class PictureUpdateRequestDto {
        private String boardPic;

        @Builder
        public PictureUpdateRequestDto(String boardPic) {
            this.boardPic = boardPic;
        }
    }

    /**
     * select picture dto
     */
    @Getter
    @Setter
    public static class PictureResponseDto {
        private int boardNo;
        private List<String> board_pic;

        public PictureResponseDto(Picture picture) {
            this.boardNo = picture.getPicNo();
        }
    }
}
