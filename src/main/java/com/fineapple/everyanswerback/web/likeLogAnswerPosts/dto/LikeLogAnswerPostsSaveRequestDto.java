package com.fineapple.everyanswerback.web.likeLogAnswerPosts.dto;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeLogAnswerPostsSaveRequestDto {
    private Long userId;
    private Long answerPostId;

    @Builder
    public LikeLogAnswerPostsSaveRequestDto(Long userId, Long answerPostId) {
        this.userId = userId;
        this.answerPostId = answerPostId;
    }

    public LikeLogAnswerPosts toEntity(Users user, AnswerPosts answerPost) {
        return LikeLogAnswerPosts.builder()
                .user(user)
                .answerPost(answerPost)
                .build();
    }
}
