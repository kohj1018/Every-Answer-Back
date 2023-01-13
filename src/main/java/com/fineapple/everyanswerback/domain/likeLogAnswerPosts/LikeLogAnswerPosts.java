package com.fineapple.everyanswerback.domain.likeLogAnswerPosts;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "likeLog_answer_posts")
public class LikeLogAnswerPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeLog_answer_posts_id")
    private Long likeLogAnswerPostsId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_post_id", nullable = false)
    private AnswerPosts answerPost;

    @Builder
    public LikeLogAnswerPosts(Users user, AnswerPosts answerPost) {
        this.user = user;
        this.answerPost = answerPost;
    }
}
