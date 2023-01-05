package com.fineapple.everyanswerback.web;

import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.dto.UsersSaveRequestDto;
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
public class UsersApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    public void tearDown() throws Exception {
        usersRepository.deleteAll();
    }

    @Test
    public void Users_등록된다() throws Exception {
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //given
        String nickname = "test1";
        String deptName = "컴공";
        String univ = "한양대";
        int entranceYear = 20;
        String oauthId = "testOauth";
        String refreshToken = "testRefreshToken";
        Boolean isDelete = true;


        UsersSaveRequestDto requestDto = UsersSaveRequestDto.builder()
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .build();

        String url = "http://localhost:" + port + "/api/v1/users";

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Users> all = usersRepository.findAll();
        assertThat(all.get(0).getNickname()).isEqualTo(nickname);
        assertThat(all.get(0).getDeptName()).isEqualTo(deptName);
        assertThat(all.get(0).getUniv()).isEqualTo(univ);
        assertThat(all.get(0).getEntranceYear()).isEqualTo(entranceYear);
        assertThat(all.get(0).getOauthId()).isEqualTo(oauthId);
        assertThat(all.get(0).getRefreshToken()).isEqualTo(refreshToken);
        assertThat(all.get(0).getIsDelete()).isEqualTo(isDelete);
        assertThat(all.get(0).getCreatedAt()).isAfter(time);
        assertThat(all.get(0).getUpdatedAt()).isAfter(time);
        System.out.println(all.get(0).getCreatedAt());
    }
}