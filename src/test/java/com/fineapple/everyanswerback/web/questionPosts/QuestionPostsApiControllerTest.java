package com.fineapple.everyanswerback.web.questionPosts;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QuestionPostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private QuestionPostsRepository questionPostsRepository;

    @Autowired
    private DeptClassRepository deptClassRepository;

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    public void tearDown() throws Exception {
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void QuestionPosts_등록된다() throws Exception {
        //given
        // DeptClass 저장
        Long deptId = 100L;
        DeptClass deptClass = DeptClass.builder()
                .deptId(deptId)
                .college("소융대")
                .name("소프트웨어")
                .build();

        deptClassRepository.save(deptClass);

        // Users 저장
        String nickname = "병욱고";
        Users user = Users.builder()
                .deptClass(deptClass)
                .nickname(nickname)
                .deptName("컴공")
                .univ("한양대")
                .entranceYear(20)
                .oauthId("dafsdafdafs")
                .refreshToken("fdsajflkdj")
                .isDelete(false)
                .build();

        usersRepository.save(user);

        // QuestionPostsSaveRequestDto 생성
        String title = "test 제목";
        String content = "test 내용";

        QuestionPostsSaveRequestDto requestDto = QuestionPostsSaveRequestDto.builder()
                .user(user)
                .deptClass(deptClass)
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/questionPosts";

        // 비교를 위한 기준 시간 변수 초기화
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<QuestionPosts> all = questionPostsRepository.findAll();
        assertThat(all.get(0).getUser().getNickname()).isEqualTo(nickname);
        assertThat(all.get(0).getDeptClass().getDeptId()).isEqualTo(deptId);
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getCreatedAt()).isAfter(time);
    }
}