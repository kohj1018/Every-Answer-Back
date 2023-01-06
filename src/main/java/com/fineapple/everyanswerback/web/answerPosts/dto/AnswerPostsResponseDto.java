package com.fineapple.everyanswerback.web.answerPosts.dto;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsResponseDto;
import com.fineapple.everyanswerback.web.users.dto.UsersResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AnswerPostsResponseDto {
    private Long answerPostId;
    private QuestionPostsResponseDto questionPost;
    private UsersResponseDto user;
    private Long likeNum;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public AnswerPostsResponseDto(AnswerPosts entity) {
        this.answerPostId = entity.getAnswerPostId();
        this.questionPost = new QuestionPostsResponseDto(entity.getQuestionPost());
        this.user = new UsersResponseDto(entity.getUser());
        this.likeNum = entity.getLikeNum();

        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }
}


