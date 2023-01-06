package com.fineapple.everyanswerback.web.likeLogAnswerPosts;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPosts;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPostsRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.likeLogAnswerPosts.dto.LikeLogAnswerPostsSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LikeLogAnswerPostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

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

    // DeptClass 저장
    DeptClass deptClass = DeptClass.builder()
            .deptId(100L)
            .college("소융대")
            .name("소프트웨어")
            .build();

    // DeptClass2 저장
    DeptClass deptClass2 = DeptClass.builder()
            .deptId(200L)
            .college("소융대2")
            .name("소프트웨어2")
            .build();

    // Users 저장
    Users user = Users.builder()
            .deptClass(deptClass)
            .nickname("병욱고")
            .deptName("컴공")
            .univ("한양대")
            .entranceYear(20)
            .oauthId("dafsdafdafs")
            .refreshToken("fdsajflkdj")
            .isDelete(false)
            .build();

    // Users2 저장
    Users user2 = Users.builder()
            .deptClass(deptClass2)
            .nickname("병욱고2")
            .deptName("컴공2")
            .univ("한양대2")
            .entranceYear(22)
            .oauthId("dafsdafdafs2")
            .refreshToken("fdsajflkdj2")
            .isDelete(false)
            .build();

    // QuestionPosts 저장
    QuestionPosts questionPost = QuestionPosts.builder()
            .user(user)
            .deptClass(deptClass)
            .title("test 질문글 제목")
            .content("test 질문글 본문")
            .build();

    // AnswerPosts 저장
    AnswerPosts answerPost = AnswerPosts.builder()
            .questionPost(questionPost)
            .user(user2)
            .content("test 답변글 본문")
            .build();


    @BeforeEach
    public void setup() throws Exception {
        deptClassRepository.save(deptClass);
        deptClassRepository.save(deptClass2);
        usersRepository.save(user);
        usersRepository.save(user2);
        questionPostsRepository.save(questionPost);
        answerPostsRepository.save(answerPost);
    }

    @AfterEach
    public void tearDown() throws Exception {   //TODO: 밑의 테스트코드 두 개 동시에 돌릴 때 문제 있음 나중에 테스트코드 수정
        likeLogAnswerPostsRepository.deleteAll();
        answerPostsRepository.deleteAll();
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void LikeLogAnswerPosts_등록된다() throws Exception {
        //given
        // LikeLogAnswerPostsSaveRequestDto 생성
        LikeLogAnswerPostsSaveRequestDto requestDto = LikeLogAnswerPostsSaveRequestDto.builder()
                .user(user)
                .answerPost(answerPost)
                .build();

        String url = "http://localhost:" + port + "/api/v1/likeLogAnswerPosts";

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<LikeLogAnswerPosts> all = likeLogAnswerPostsRepository.findAll();
        assertThat(all.get(0).getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(all.get(0).getAnswerPost().getAnswerPostId()).isEqualTo(answerPost.getAnswerPostId());
    }

    @Test
    public void LikeLogAnswerPosts_제거된다() throws Exception {
        //given
        likeLogAnswerPostsRepository.save(LikeLogAnswerPosts.builder()
                .user(user)
                .answerPost(answerPost)
                .build());

        Long userId = user.getUserId();
        Long answerPostId = answerPost.getAnswerPostId();

        String url = "http://localhost:" + port + "/api/v1/likeLogAnswerPosts?userId=" + userId + "&answerPostId=" + answerPostId;

        //when
        testRestTemplate.delete(url);

        //then
        List<LikeLogAnswerPosts> all = likeLogAnswerPostsRepository.findAll();
        assertThat(all).isEmpty();
    }
}