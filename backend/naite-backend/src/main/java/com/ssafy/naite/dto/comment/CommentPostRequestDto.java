package com.ssafy.naite.dto.comment;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPostRequestDto {
    private int boardId;
    private String userToken;
    private int parentId; // 원댓글: 0, 답글: 원댓글 아이디
    private String content;

}
