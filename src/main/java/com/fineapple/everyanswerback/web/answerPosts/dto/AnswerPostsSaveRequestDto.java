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
    private QuestionPosts questionPost;
    private Users user;
    private Long likeNum;
    private String content;

    @Builder
    public AnswerPostsSaveRequestDto(QuestionPosts questionPost, Users user, String content) {
        this.questionPost = questionPost;
        this.user = user;
        this.likeNum = 0L;  // default value
        this.content = content;
    }

    public AnswerPosts toEntity() {
        return AnswerPosts.builder()
                .questionPost(questionPost)
                .user(user)
                .likeNum(likeNum)
                .content(content)
                .build();
    }
}
