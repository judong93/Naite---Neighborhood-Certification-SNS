package com.ssafy.naite.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserGetProfileResponseDto {
    private String userName;
    private String userNick;
    private int userScore;
    private int boardCnt;
    private int commentCnt;
    private int dealCnt;
    private String userPic;

    @Builder
    public UserGetProfileResponseDto(String userName, String userNick, int userScore, int boardCnt, int commentCnt, int dealCnt, String userPic) {
        this.userName = userName;
        this.userNick = userNick;
        this.userScore = userScore;
        this.boardCnt = boardCnt;
        this.commentCnt = commentCnt;
        this.dealCnt = dealCnt;
        this.userPic = userPic;
    }
}
