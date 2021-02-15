package com.ssafy.naite.dto.comment;

import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class CommentGetResponseDto {
    private Integer commentNo;
    private String userNick;
    private String userPic;
    private String createdAt;
    private String updatedAt;
    private String content;
    private int parentId;
    private boolean userOwn;
    private byte isUnknown;
    private byte isDeleted;

    public CommentGetResponseDto(Integer commentNo, String userNick, String userPic, String createdAt, String updatedAt, String content, int parentId, boolean userOwn, byte isUnknown, byte isDeleted) {
        this.commentNo = commentNo;
        this.userNick = userNick;
        this.userPic = userPic;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.content = content;
        this.parentId = parentId;
        this.userOwn = userOwn;
        this.isUnknown = isUnknown;
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "CommentGetResponseDto{" +
                "commentNo=" + commentNo +
                ", userNick='" + userNick + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", content='" + content + '\'' +
                ", parentId=" + parentId +
                ", userOwn=" + userOwn +
                ", isUnknown=" + isUnknown +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
