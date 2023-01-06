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
    private Users user;
    private AnswerPosts answerPost;

    @Builder
    public LikeLogAnswerPostsSaveRequestDto(Users user, AnswerPosts answerPost) {
        this.user = user;
        this.answerPost = answerPost;
    }

    public LikeLogAnswerPosts toEntity() {
        return LikeLogAnswerPosts.builder()
                .user(user)
                .answerPost(answerPost)
                .build();
    }
}
