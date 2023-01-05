package com.fineapple.everyanswerback.domain.users;

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
class UsersRepositoryTest {

    @Autowired
    UsersRepository usersRepository;

    @AfterEach
    public void cleanup() {
        usersRepository.deleteAll();
    }

    @Test
    public void 신규유저_저장_및_불러오기() {
        //given
        String nickname = "test1";
        String deptName = "컴공";
        String univ = "한양대";
        int entranceYear = 20;
        String oauthId = "testOauth";
        String refreshToken = "testRefreshToken";
        boolean isDelete = true;

        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        usersRepository.save(Users.builder()
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .build());

        //when
        List<Users> usersList = usersRepository.findAll();

        //then
        Users users = usersList.get(0);
        assertThat(users.getNickname()).isEqualTo(nickname);
        assertThat(users.getDeptName()).isEqualTo(deptName);
        assertThat(users.getUniv()).isEqualTo(univ);
        assertThat(users.getEntranceYear()).isEqualTo(entranceYear);
        assertThat(users.getOauthId()).isEqualTo(oauthId);
        assertThat(users.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(users.getIsDelete()).isEqualTo(isDelete);
        System.out.println(users.getIsDelete());
        assertThat(users.getCreatedAt()).isAfter(time);
        assertThat(users.getUpdatedAt()).isAfter(time);
        System.out.println(users.getCreatedAt());
    }
}