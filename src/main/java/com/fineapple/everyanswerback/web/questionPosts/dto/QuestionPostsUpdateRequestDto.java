package com.fineapple.everyanswerback.web.questionPosts.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionPostsUpdateRequestDto {
    private DeptClass deptClass;
    private String title;
    private String content;

    @Builder
    public QuestionPostsUpdateRequestDto(DeptClass deptClass, String title, String content) {
        this.deptClass = deptClass;
        this.title = title;
        this.content = content;
    }
}
