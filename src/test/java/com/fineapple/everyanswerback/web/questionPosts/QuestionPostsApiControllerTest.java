package com.fineapple.everyanswerback.web.questionPosts;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassSaveRequestDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsResponseDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    // DeptClass 저장
    Long deptId = 100L;
    DeptClass deptClass = DeptClass.builder()
            .deptId(deptId)
            .college("소융대")
            .name("소프트웨어")
            .build();

    // DeptClass2 저장
    DeptClassSaveRequestDto deptClassSaveRequestDto2 =  DeptClassSaveRequestDto.builder()
            .deptId(200L)
            .college("소융대2")
            .name("소프트웨어2")
            .build();

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

    @BeforeEach
    public void setup() throws Exception {
        deptClassRepository.save(deptClass);
        deptClassRepository.save(deptClassSaveRequestDto2.toEntity());
        usersRepository.save(user);
    }

    @AfterEach
    public void tearDown() throws Exception {
        questionPostsRepository.deleteAll();
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void QuestionPosts_등록된다() throws Exception {
        //given
        // QuestionPostsSaveRequestDto 생성
        String title = "test 제목";
        String content = "test 내용";

        QuestionPostsSaveRequestDto requestDto = QuestionPostsSaveRequestDto.builder()
                .userId(user.getUserId())
                .deptId(deptClass.getDeptId())
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

    @Test
    public void QuestionPosts_수정된다() throws Exception {
        //given

        // 초기 저장 값
        // QuestionPosts 저장
        String title = "test 제목";
        String content = "test 본문";

        QuestionPosts questionPost = questionPostsRepository.save(QuestionPosts.builder()
                .user(user)
                .deptClass(deptClass)
                .title(title)
                .content(content)
                .build());

        Long updateId = questionPost.getQuestionPostId();

        // 이후 수정 값
        // QuestionPostsUpdateRequestDto 생성
        String title2 = "test 제목2";
        String content2 = "test 본문2";

        QuestionPostsUpdateRequestDto requestDto = QuestionPostsUpdateRequestDto.builder()
                .deptId(deptClassSaveRequestDto2.getDeptId())
                .title(title2)
                .content(content2)
                .build();

        String url = "http://localhost:" + port + "/api/v1/questionPosts/" + updateId;

        HttpEntity<QuestionPostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<QuestionPosts> all = questionPostsRepository.findAll();
        assertThat(all.get(0).getDeptClass().getDeptId()).isEqualTo(deptClassSaveRequestDto2.getDeptId());
        assertThat(all.get(0).getTitle()).isEqualTo(title2);
        assertThat(all.get(0).getContent()).isEqualTo(content2);
        assertThat(all.get(0).getUpdatedAt()).isNotNull();
    }

//    @Test
//    public void QuestionPosts_무한스크롤() throws Exception {
//        //given
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//        questionPostsRepository.save(QuestionPosts.builder()
//                .user(user)
//                .deptClass(deptClass)
//                .title("테스트")
//                .content("테스트")
//                .build());
//
//        // 52개 (id 1번부터 시작)
//        Long lastPostId = 40L;
//        int size = 20;
//        Long lastPostId2 = 5L;
//        int size2 = 10;
//
//        String url = "http://localhost:" + port + "/api/v1/questionPosts?lastPostId=" + lastPostId + "&size=" + size;
//        String url2 = "http://localhost:" + port + "/api/v1/questionPosts?lastPostId=" + lastPostId2 + "&size=" + size2;
//
//        //when
//        ResponseEntity<QuestionPostsResponseDto[]> responseEntity = testRestTemplate.getForEntity(url, QuestionPostsResponseDto[].class);
//        ResponseEntity<QuestionPostsResponseDto[]> responseEntity2 = testRestTemplate.getForEntity(url2, QuestionPostsResponseDto[].class);
//
//        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity2.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity2.getBody()).isNotNull();
//
//        List<QuestionPostsResponseDto> questionPostsList = Arrays.asList(responseEntity.getBody());
//        assertThat(questionPostsList.get(0).getQuestionPostId()).isEqualTo(lastPostId-1);
//        assertThat(questionPostsList.size()).isEqualTo(size);
//
//        List<QuestionPostsResponseDto> questionPostsList2 = Arrays.asList(responseEntity2.getBody());
//        assertThat(questionPostsList2.get(0).getQuestionPostId()).isEqualTo(lastPostId2-1);
//        assertThat(questionPostsList2.size()).isEqualTo(4);
//    }
}