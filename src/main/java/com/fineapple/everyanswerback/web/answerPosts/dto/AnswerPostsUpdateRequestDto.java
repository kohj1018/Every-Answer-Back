package com.fineapple.everyanswerback.web.answerPosts.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerPostsUpdateRequestDto {
    private String content;

    public AnswerPostsUpdateRequestDto(String content) {
        this.content = content;
    }
}
