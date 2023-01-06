package com.fineapple.everyanswerback.domain.likeLogAnswerPosts;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LikeLogAnswerPostsRepositoryTest {

    @Autowired
    LikeLogAnswerPostsRepository likeLogAnswerPostsRepository;

    @Autowired
    DeptClassRepository deptClassRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    QuestionPostsRepository questionPostsRepository;

    @Autowired
    AnswerPostsRepository answerPostsRepository;

    @AfterEach
    public void cleanup() {
        likeLogAnswerPostsRepository.deleteAll();
        answerPostsRepository.deleteAll();
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void 추천기록_저장_및_불러오기() {
        //given
        // DeptClass 저장
        DeptClass deptClass = DeptClass.builder()
                .deptId(100L)
                .college("소융대")
                .name("소프트웨어")
                .build();

        deptClassRepository.save(deptClass);

        // Users 저장
        Users user = Users.builder()
                .deptClass(deptClass)
                .nickname("test1")
                .deptName("컴공")
                .univ("한양대")
                .entranceYear(20)
                .oauthId("testOauth")
                .refreshToken("testRefreshToken")
                .isDelete(false)
                .build();

        usersRepository.save(user);

        // QuestionPosts 저장
        QuestionPosts questionPost = QuestionPosts.builder()
                .user(user)
                .deptClass(deptClass)
                .title("test 제목")
                .content("test 내용")
                .build();

        questionPostsRepository.save(questionPost);

        // AnswerPosts 저장
        AnswerPosts answerPost = AnswerPosts.builder()
                .questionPost(questionPost)
                .user(user)
                .content("test 답변 본문입니다.")
                .build();

        answerPostsRepository.save(answerPost);

        // LikeLogAnswerPosts 저장
        likeLogAnswerPostsRepository.save(LikeLogAnswerPosts.builder()
                .user(user)
                .answerPost(answerPost)
                .build());

        //when
        List<LikeLogAnswerPosts> likeLogAnswerPostsList = likeLogAnswerPostsRepository.findAll();

        //then
        LikeLogAnswerPosts likeLogAnswerPost = likeLogAnswerPostsList.get(0);
        assertThat(likeLogAnswerPost.getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(likeLogAnswerPost.getAnswerPost().getAnswerPostId()).isEqualTo(answerPost.getAnswerPostId());
    }
}