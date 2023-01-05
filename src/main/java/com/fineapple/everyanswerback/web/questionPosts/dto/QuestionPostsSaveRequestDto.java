package com.fineapple.everyanswerback.web.questionPosts.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionPostsSaveRequestDto {
    private Users users;
    private DeptClass deptClass;
    private String title;
    private String content;

    @Builder
    public QuestionPostsSaveRequestDto(Users users, DeptClass deptClass, String title, String content) {
        this.users = users;
        this.deptClass = deptClass;
        this.title = title;
        this.content = content;
    }

    public QuestionPosts toEntity() {
        return QuestionPosts.builder()
                .users(users)
                .deptClass(deptClass)
                .title(title)
                .content(content)
                .build();
    }
}
