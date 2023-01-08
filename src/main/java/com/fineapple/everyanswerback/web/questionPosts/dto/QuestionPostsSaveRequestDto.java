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
    private Long userId;
    private Long deptClassId;
    private String title;
    private String content;

    @Builder
    public QuestionPostsSaveRequestDto(Long userId, Long deptClassId, String title, String content) {
        this.userId = userId;
        this.deptClassId = deptClassId;
        this.title = title;
        this.content = content;
    }

    public QuestionPosts toEntity(Users user, DeptClass deptClass) {
        return QuestionPosts.builder()
                .user(user)
                .deptClass(deptClass)
                .title(title)
                .content(content)
                .build();
    }
}
