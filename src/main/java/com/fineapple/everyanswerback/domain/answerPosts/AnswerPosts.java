package com.fineapple.everyanswerback.domain.answerPosts;

import com.fineapple.everyanswerback.domain.BaseTimeEntity;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "answer_posts")
public class AnswerPosts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_post_id")
    private Long answerPostId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_post_id", nullable = false)
    private QuestionPosts questionPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false)
    private Long likeNum;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "answerPost") // likeLogAnswerPosts 와의 양방향 매핑을 위해 추가
    private List<LikeLogAnswerPosts> likeLogAnswerPostsList = new ArrayList<>();

    @Builder
    public AnswerPosts(QuestionPosts questionPost, Users user, String content) {
        this.questionPost = questionPost;
        this.user = user;
        this.likeNum = 0L;  // default value
        this.content = content;
    }

    public void update(AnswerPostsUpdateRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
