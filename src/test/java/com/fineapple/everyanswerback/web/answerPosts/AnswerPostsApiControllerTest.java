package com.fineapple.everyanswerback.web.answerPosts;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsResponseDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnswerPostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private AnswerPostsRepository answerPostsRepository;

    @Autowired
    private DeptClassRepository deptClassRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private QuestionPostsRepository questionPostsRepository;

    // DeptClass ??????
    Long deptId = 100L;
    DeptClass deptClass = DeptClass.builder()
            .deptId(deptId)
            .college("?????????")
            .name("???????????????")
            .build();

    // DeptClass2 ??????
    Long deptId2 = 200L;
    DeptClass deptClass2 = DeptClass.builder()
            .deptId(deptId2)
            .college("?????????2")
            .name("???????????????2")
            .build();

    // Users ??????
    String nickname = "?????????";
    Users user = Users.builder()
            .deptClass(deptClass)
            .nickname(nickname)
            .deptName("??????")
            .univ("?????????")
            .entranceYear(20)
            .oauthId("dafsdafdafs")
            .refreshToken("fdsajflkdj")
            .isDelete(false)
            .agreeTerms(true)
            .isCertified(true)
            .build();

    // Users2 ??????
    String nickname2 = "?????????2";
    Users user2 = Users.builder()
            .deptClass(deptClass2)
            .nickname(nickname2)
            .deptName("??????2")
            .univ("?????????2")
            .entranceYear(22)
            .oauthId("dafsdafdafs2")
            .refreshToken("fdsajflkdj2")
            .isDelete(false)
            .agreeTerms(true)
            .isCertified(true)
            .build();

    // QuestionPosts ??????
    String questionTitle = "test ????????? ??????";
    String questionContent = "test ????????? ??????";
    QuestionPosts questionPost = QuestionPosts.builder()
            .user(user)
            .deptClass(deptClass)
            .title(questionTitle)
            .content(questionContent)
            .build();

    // QuestionPosts2 ??????
    String questionTitle2 = "test ????????? ??????2";
    String questionContent2 = "test ????????? ??????2";
    QuestionPosts questionPost2 = QuestionPosts.builder()
            .user(user2)
            .deptClass(deptClass2)
            .title(questionTitle2)
            .content(questionContent2)
            .build();

    @BeforeEach
    public void setup() throws Exception {
        deptClassRepository.save(deptClass);
        deptClassRepository.save(deptClass2);
        usersRepository.save(user);
        usersRepository.save(user2);
        questionPostsRepository.save(questionPost);
        questionPostsRepository.save(questionPost2);
    }

    @AfterEach
    public void tearDown() throws Exception {
        answerPostsRepository.deleteAll();
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void AnswerPosts_????????????() throws Exception {
        //given
        // AnswerPostsSaveRequestDto ??????
        String content = "test ????????? ??????";

        AnswerPostsSaveRequestDto requestDto = AnswerPostsSaveRequestDto.builder()
                .questionPostId(questionPost.getQuestionPostId())
                .userId(user2.getUserId())
                .content(content)
                .build();

        String url = "http://localhost:" + port + "/api/v1/answerPosts";

        // ????????? ?????? ?????? ?????? ?????? ?????????
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<AnswerPosts> all = answerPostsRepository.findAll();
        assertThat(all.get(0).getQuestionPost().getQuestionPostId()).isEqualTo(questionPost.getQuestionPostId());
        assertThat(all.get(0).getUser().getUserId()).isEqualTo(user2.getUserId());
        assertThat(all.get(0).getLikeNum()).isEqualTo(0);
        assertThat(all.get(0).getContent()).isEqualTo(content);
        assertThat(all.get(0).getCreatedAt()).isAfter(time);
    }

    @Test
    public void AnswerPosts_????????????() throws Exception {
        //given

        // ?????? ?????? ???
        // AnswerPosts ??????
        String content = "test ????????? ??????";

        AnswerPosts answerPost = answerPostsRepository.save(AnswerPosts.builder()
                .questionPost(questionPost)
                .user(user)
                .content(content)
                .build());

        Long updateId = answerPost.getAnswerPostId();

        // ?????? ?????? ???
        // AnswerPostsUpdateRequestDto ??????
        String content2 = "test ????????? ??????2";

        AnswerPostsUpdateRequestDto requestDto = new AnswerPostsUpdateRequestDto(content2);

        String url = "http://localhost:" + port + "/api/v1/answerPosts/" + updateId;

        HttpEntity<AnswerPostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<AnswerPosts> all = answerPostsRepository.findAll();
        assertThat(all.get(0).getContent()).isEqualTo(content2);
        assertThat(all.get(0).getUpdatedAt()).isNotNull();
    }

    @Test
    public void QuestionPost???_??????_AnswerPostsList???_????????????() {
        //given
        String content = "test ????????? ??????";
        String content2 = "test ????????? ??????2";
        String content3 = "test ????????? ??????3";

        // QuestionPost??? ?????? ?????????
        answerPostsRepository.save(AnswerPosts.builder()
                .questionPost(questionPost)
                .user(user)
                .content(content)
                .build());

        // QuestionPost2??? ?????? ?????????
        answerPostsRepository.save(AnswerPosts.builder()
                .questionPost(questionPost2)
                .user(user2)
                .content(content2)
                .build());

        // QuestionPost2??? ?????? ?????????2
        answerPostsRepository.save(AnswerPosts.builder()
                .questionPost(questionPost2)
                .user(user)
                .content(content3)
                .build());

        // ???????????? QuestionPost??? Id ?????????
        List<QuestionPosts> questionPostsList = questionPostsRepository.findAll();
        Long idOfQuestionPostToFind = questionPostsList.get(1).getQuestionPostId();

        String url = "http://localhost:" + port + "/api/v1/answerPosts?questionPostId=" + idOfQuestionPostToFind;

        // ????????? ?????? ?????? ?????? ?????? ?????????
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        ResponseEntity<AnswerPostsResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, AnswerPostsResponseDto[].class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        List<AnswerPostsResponseDto> answerPostsList = Arrays.asList(responseEntity.getBody());
        assertThat(answerPostsList.get(1).getUser().getUserId()).isEqualTo(user2.getUserId());
        assertThat(answerPostsList.get(0).getUser().getUserId()).isEqualTo(user.getUserId());
        assertThat(answerPostsList.get(1).getContent()).isEqualTo(content2);
        assertThat(answerPostsList.get(0).getContent()).isEqualTo(content3);
        assertThat(answerPostsList.get(1).getCreatedAt()).isAfter(time);
    }

    @Test
    public void ??????_?????????_?????????_??????_QuestionPost???_AnswerPostsList_??????_?????????() {
        //given
        // ???????????? QuestionPost??? Id ?????????
        List<QuestionPosts> questionPostsList = questionPostsRepository.findAll();
        Long idOfQuestionPostToFind = questionPostsList.get(0).getQuestionPostId();

        String url = "http://localhost:" + port + "/api/v1/answerPosts?questionPostId=" + idOfQuestionPostToFind;

        //when
        ResponseEntity<AnswerPostsResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, AnswerPostsResponseDto[].class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();

        List<AnswerPostsResponseDto> answerPostsList = Arrays.asList(responseEntity.getBody());
        assertThat(answerPostsList).isEmpty();
        System.out.println("?????? : ");
        System.out.println(answerPostsList);
    }
}