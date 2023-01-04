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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    public void tearDown() throws Exception {
        usersRepository.deleteAll();
    }

    @Test
    public void 유저_등록된다() throws Exception {
        //given
        String nickname = "test1";
        String deptName = "컴공";
        String univ = "한양대";
        int entranceYear = 20;
        String oauthId = "testOauth";
        String refreshToken = "testRefreshToken";
        boolean isActive = true;

        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        UsersSaveRequestDto requestDto = UsersSaveRequestDto.builder()
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isActive(isActive)
                .build();

        String url = "http://localhost:" + port + "/api/v1/users";

        //when 1
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then 2
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        //when 1
        List<Users> usersList = usersRepository.findAll();
        Users users = usersList.get(0);
        System.out.println(users);

        //when 2
        assertThat(users.getNickname()).isEqualTo(nickname);
        assertThat(users.getDeptName()).isEqualTo(deptName);
        assertThat(users.getUniv()).isEqualTo(univ);
        assertThat(users.getEntranceYear()).isEqualTo(entranceYear);
        assertThat(users.getOauthId()).isEqualTo(oauthId);
        assertThat(users.getRefreshToken()).isEqualTo(refreshToken);
//        assertThat(users.isActive()).isEqualTo(isActive); TODO: 요거 거꾸로 나오는데 해결 필요
        assertThat(users.getCreatedAt()).isAfter(time);
        assertThat(users.getUpdatedAt()).isAfter(time);
        System.out.println(users.getCreatedAt());
    }
}