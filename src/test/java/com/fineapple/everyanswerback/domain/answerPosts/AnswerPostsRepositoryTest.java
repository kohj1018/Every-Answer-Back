package com.fineapple.everyanswerback.domain.answerPosts;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class AnswerPostsRepositoryTest {

    @Autowired
    AnswerPostsRepository answerPostsRepository;

    @Autowired
    DeptClassRepository deptClassRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    QuestionPostsRepository questionPostsRepository;

    @AfterEach
    public void cleanup() {
        answerPostsRepository.deleteAll();
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void 답변글_저장_및_불러오기() {
        //given
        // DeptClass 저장
        DeptClass deptClass = DeptClass.builder()
                .deptId(100L)
                .college("소융대")
                .name("소프트웨어")
                .build();

        deptClassRepository.save(deptClass);

        // Users 저장
        String nickname = "test1";
        String deptName = "컴공";
        String univ = "한양대";
        int entranceYear = 20;
        String oauthId = "testOauth";
        String refreshToken = "testRefreshToken";
        boolean isDelete = true;
        boolean agreeTerms = true;
        boolean isCertified = false;

        Users user = Users.builder()
                .deptClass(deptClass)
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .agreeTerms(agreeTerms)
                .isCertified(isCertified)
                .build();

        usersRepository.save(user);

        // QuestionPosts 저장
        String questionTitle = "test 제목";
        String questionContent = "test 내용";

        QuestionPosts questionPost = QuestionPosts.builder()
                .user(user)
                .deptClass(deptClass)
                .title(questionTitle)
                .content(questionContent)
                .build();

        questionPostsRepository.save(questionPost);

        // AnswerPosts 저장
        String answerContent = "test 답변 본문입니다.";

        answerPostsRepository.save(AnswerPosts.builder()
                .questionPost(questionPost)
                .user(user)
                .content(answerContent)
                .build());


        // 비교를 위한 기준 시간 변수 초기화
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        List<AnswerPosts> answerPostsList = answerPostsRepository.findAll();

        //then
        AnswerPosts answerPost = answerPostsList.get(0);
        assertThat(answerPost.getQuestionPost().getQuestionPostId()).isEqualTo(questionPost.getQuestionPostId());
        assertThat(answerPost.getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(answerPost.getContent()).isEqualTo(answerContent);
        assertThat(answerPost.getCreatedAt()).isAfter(time);
    }
}