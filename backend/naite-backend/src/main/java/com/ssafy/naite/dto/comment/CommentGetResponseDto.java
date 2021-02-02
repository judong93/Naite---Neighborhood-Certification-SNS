package com.ssafy.naite.dto.comment;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommentGetResponseDto {
    @ApiParam(value = "유저 닉네임")
    private String userNick;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String content;
    private int parentId;
    private boolean userOwn;

    @Builder
    public CommentGetResponseDto(String userNick, Timestamp createdAt, Timestamp updatedAt, String content, int parentId, boolean userOwn) {
        this.userNick = userNick;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.parentId = parentId;
        this.userOwn = userOwn;
    }

}
