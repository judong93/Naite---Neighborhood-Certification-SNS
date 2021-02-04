package com.ssafy.naite.dto.comment;

import io.swagger.annotations.Api;
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

    @ApiParam(value = "등록할 댓글 내용")
    private String content;

    @ApiParam(value = "익명 여부")
    private int isUnknown;

    @Override
    public String toString() {
        return "CommentPostRequestDto{" +
                "boardId=" + boardId +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", isUnknown=" + isUnknown +
                '}';
    }
}
