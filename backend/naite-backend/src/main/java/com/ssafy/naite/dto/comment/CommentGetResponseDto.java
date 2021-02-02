package com.ssafy.naite.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommentGetResponseDto {
    private String userNick;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String content;
    private int parentId;

    @Builder
    public CommentGetResponseDto(String userNick, Timestamp createdAt, Timestamp updatedAt, String content, int parentId) {
        this.userNick = userNick;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.parentId = parentId;
    }
}
