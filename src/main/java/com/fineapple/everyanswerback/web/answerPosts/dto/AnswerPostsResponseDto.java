package com.fineapple.everyanswerback.web.answerPosts.dto;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.web.users.dto.OtherUsersResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AnswerPostsResponseDto {
    private Long answerPostId;
    private Long questionPostId;
    private OtherUsersResponseDto user;
    private Long likeNum;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnswerPostsResponseDto(AnswerPosts entity) {
        this.answerPostId = entity.getAnswerPostId();
        this.questionPostId = entity.getQuestionPost().getQuestionPostId();
        this.user = new OtherUsersResponseDto(entity.getUser());
        this.likeNum = (long) entity.getLikeLogAnswerPostsList().size();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}


