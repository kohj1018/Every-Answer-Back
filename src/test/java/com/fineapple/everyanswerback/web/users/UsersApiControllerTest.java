package com.fineapple.everyanswerback.web.users;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassSaveRequestDto;
import com.fineapple.everyanswerback.web.users.dto.UsersSaveRequestDto;
import com.fineapple.everyanswerback.web.users.dto.UsersUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
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
    private DeptClassRepository deptClassRepository;

    @Autowired
    private UsersRepository usersRepository;

    @AfterEach
    public void tearDown() throws Exception {
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void Users_등록된다() throws Exception {
        //given
        // DeptClass 저장
        DeptClass deptClass = DeptClass.builder()
                .deptId(100L)
                .college("소융대")
                .name("소프트웨어")
                .build();

        deptClassRepository.save(deptClass);

        // UsersSaveRequestDto 생성
        String nickname = "test1";
        String deptName = "컴공";
        String univ = "한양대";
        int entranceYear = 20;
        String oauthId = "testOauth";
        String refreshToken = "testRefreshToken";
        Boolean isDelete = true;

        UsersSaveRequestDto requestDto = UsersSaveRequestDto.builder()
                .deptClass(deptClass)
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .build();

        String url = "http://localhost:" + port + "/api/v1/users";

        // 비교를 위한 기준 시간 변수 초기화
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Users> all = usersRepository.findAll();
        assertThat(all.get(0).getDeptClass().getDeptId()).isEqualTo(deptClass.getDeptId());
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

    @Test
    public void Users_수정된다() throws Exception {
        //given

        // 초기 저장 값
        // DeptClass 저장
        DeptClassSaveRequestDto deptClassSaveRequestDto = DeptClassSaveRequestDto.builder()
                .deptId(100L)
                .college("소융대")
                .name("소프트웨어")
                .build();

        deptClassRepository.save(deptClassSaveRequestDto.toEntity());

        // Users 저장
        String nickname = "test1";
        String deptName = "컴공";
        String univ = "한양대";
        int entranceYear = 20;
        String oauthId = "testOauth";
        String refreshToken = "testRefreshToken";
        Boolean isDelete = true;

        Users savedUsers = usersRepository.save(Users.builder()
                .deptClass(deptClassSaveRequestDto.toEntity())
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .build());

        Long updateId = savedUsers.getUserId();

        // 이후 수정 값
        // DeptClass2 저장
        DeptClassSaveRequestDto deptClassSaveRequestDto2 =  DeptClassSaveRequestDto.builder()
                .deptId(200L)
                .college("소융대2")
                .name("소프트웨어2")
                .build();

        deptClassRepository.save(deptClassSaveRequestDto2.toEntity());

        // UsersUpdateRequestDto 생성
        String nickname2 = "test2";
        String deptName2 = "컴공2";
        String univ2 = "한양대2";
        int entranceYear2 = 22;
        String oauthId2 = "testOauth2";
        String refreshToken2 = "testRefreshToken2";
        Boolean isDelete2 = false;

        UsersUpdateRequestDto requestDto = UsersUpdateRequestDto.builder()
                .deptClass(deptClassSaveRequestDto2.toEntity())
                .nickname(nickname2)
                .deptName(deptName2)
                .univ(univ2)
                .entranceYear(entranceYear2)
                .oauthId(oauthId2)
                .refreshToken(refreshToken2)
                .isDelete(isDelete2)
                .build();

        String url = "http://localhost:" + port + "/api/v1/users/" + updateId;

        HttpEntity<UsersUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Users> all = usersRepository.findAll();
        assertThat(all.get(0).getDeptClass().getDeptId()).isEqualTo(deptClassSaveRequestDto2.getDeptId());
        assertThat(all.get(0).getNickname()).isEqualTo(nickname2);
        assertThat(all.get(0).getDeptName()).isEqualTo(deptName2);
        assertThat(all.get(0).getUniv()).isEqualTo(univ2);
        assertThat(all.get(0).getEntranceYear()).isEqualTo(entranceYear2);
        assertThat(all.get(0).getOauthId()).isEqualTo(oauthId2);
        assertThat(all.get(0).getRefreshToken()).isEqualTo(refreshToken2);
        assertThat(all.get(0).getIsDelete()).isEqualTo(isDelete2);
        assertThat(all.get(0).getUpdatedAt()).isNotNull();
    }
}