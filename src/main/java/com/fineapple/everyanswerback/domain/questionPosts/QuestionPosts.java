package com.fineapple.everyanswerback.domain.questionPosts;

import com.fineapple.everyanswerback.domain.BaseTimeEntity;
import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "question_posts")
public class QuestionPosts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_post_id")
    private Long questionPostId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "dept_id")
    private DeptClass deptClass;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public QuestionPosts(Users users, DeptClass deptClass, String title, String content) {
        this.users = users;
        this.deptClass = deptClass;
        this.title = title;
        this.content = content;
    }

    public void update(QuestionPostsUpdateRequestDto requestDto) {
        this.deptClass = requestDto.getDeptClass();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}
