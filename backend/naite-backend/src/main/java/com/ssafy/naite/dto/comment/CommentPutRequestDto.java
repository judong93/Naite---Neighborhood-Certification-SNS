package com.ssafy.naite.dto.comment;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentPutRequestDto {
    private String commentContent;

    @Builder
    public CommentPutRequestDto(String commentContent) {
        this.commentContent = commentContent;
    }
}
