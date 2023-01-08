package com.fineapple.everyanswerback.web.answerPosts.dto;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerPostsSaveRequestDto {
    private Long questionPostId;
    private Long userId;
    private Long likeNum;
    private String content;

    @Builder
    public AnswerPostsSaveRequestDto(Long questionPostId, Long userId, String content) {
        this.questionPostId = questionPostId;
        this.userId = userId;
        this.likeNum = 0L;  // default value
        this.content = content;
    }

    public AnswerPosts toEntity(QuestionPosts questionPost, Users user) {
        return AnswerPosts.builder()
                .questionPost(questionPost)
                .user(user)
                .content(content)
                .build();
    }
}
