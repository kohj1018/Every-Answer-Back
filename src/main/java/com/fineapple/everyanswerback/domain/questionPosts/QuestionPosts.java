package com.fineapple.everyanswerback.domain.questionPosts;

import com.fineapple.everyanswerback.domain.BaseTimeEntity;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "question_posts")
public class QuestionPosts extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_post_id")
    private Long questionPostId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "dept_id", nullable = false)
    private DeptClass deptClass;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @OneToMany(mappedBy = "questionPost")   // AnswerPosts 와의 양방향 매핑을 위해 추가
    private List<AnswerPosts> answerPostsList = new ArrayList<>();

    @Builder
    public QuestionPosts(Users user, DeptClass deptClass, String title, String content) {
        this.user = user;
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

