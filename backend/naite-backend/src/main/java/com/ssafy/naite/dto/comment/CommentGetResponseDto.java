package com.ssafy.naite.dto.comment;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommentGetResponseDto {
    private Integer commentNo;
    @ApiParam(value = "유저 닉네임")
    private String userNick;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String content;
    private int parentId;
    private boolean userOwn;
    private byte isUnknown;
    private byte isDeleted;

    @Builder
    public CommentGetResponseDto(Integer commentNo, String userNick, Timestamp createdAt, Timestamp updatedAt, String content, int parentId, boolean userOwn, byte isUnknown, byte isDeleted) {
        this.commentNo = commentNo;
        this.userNick = userNick;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.parentId = parentId;
        this.userOwn = userOwn;
        this.isUnknown = isUnknown;
        this.isDeleted = isDeleted;
    }

}
