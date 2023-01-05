package com.fineapple.everyanswerback.web.answerPosts.dto;

import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerPostsUpdateRequestDto {
    private Long likeNum;
    private String content;

    @Builder
    public AnswerPostsUpdateRequestDto(Long likeNum, String content) {
        this.likeNum = likeNum;
        this.content = content;
    }
}
