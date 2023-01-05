package com.fineapple.everyanswerback.domain.questionPosts;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
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
class QuestionPostsRepositoryTest {

    @Autowired
    QuestionPostsRepository questionPostsRepository;

    @Autowired
    DeptClassRepository deptClassRepository;

    @Autowired
    UsersRepository usersRepository;

    @AfterEach
    public void cleanup() {
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void 질문글_저장_및_불러오기() {
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

        Users user = Users.builder()
                .deptClass(deptClass)
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .build();

        usersRepository.save(user);

        // QuestionPosts 저장
        String title = "test 제목";
        String content = "test 내용";

        questionPostsRepository.save(QuestionPosts.builder()
                .user(user)
                .deptClass(deptClass)
                .title(title)
                .content(content)
                .build());

        // 비교를 위한 기준 시간 변수 초기화
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        List<QuestionPosts> questionPostsList = questionPostsRepository.findAll();

        //then
        QuestionPosts questionPost = questionPostsList.get(0);
        assertThat(questionPost.getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(questionPost.getDeptClass().getDeptId()).isEqualTo(deptClass.getDeptId());
        assertThat(questionPost.getTitle()).isEqualTo(title);
        assertThat(questionPost.getContent()).isEqualTo(content);
        assertThat(questionPost.getCreatedAt()).isAfter(time);
    }

}