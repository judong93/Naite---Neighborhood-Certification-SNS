package com.ssafy.naite.dto.comment;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPostRequestDto {
    @ApiParam(value = "게시글 인덱스")
    private int boardId;

    @ApiParam(value = "원댓글 인덱스")
    private int parentId; // 원댓글: 0, 답글: 원댓글 아이디

    @ApiParam(value = "수정할 댓글 내용")
    private String content;

}
