package com.fineapple.everyanswerback.web.questionPosts.dto;

import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassResponseDto;
import com.fineapple.everyanswerback.web.users.dto.OtherUsersResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class QuestionPostsResponseDto {
    private Long questionPostId;
    private OtherUsersResponseDto user;
    private DeptClassResponseDto deptClass;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private int answerPostsCnt;

    public QuestionPostsResponseDto(QuestionPosts entity) {
        this.questionPostId = entity.getQuestionPostId();
        this.user = new OtherUsersResponseDto(entity.getUser());
        this.deptClass = new DeptClassResponseDto(entity.getDeptClass());
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.answerPostsCnt = entity.getAnswerPostsList().size();
    }
}
