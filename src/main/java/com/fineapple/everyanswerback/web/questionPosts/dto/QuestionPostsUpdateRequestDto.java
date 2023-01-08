package com.fineapple.everyanswerback.web.questionPosts.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionPostsUpdateRequestDto {
    private Long deptId;
    private String title;
    private String content;

    @Builder
    public QuestionPostsUpdateRequestDto(Long deptId, String title, String content) {
        this.deptId = deptId;
        this.title = title;
        this.content = content;
    }
}
