package com.fineapple.everyanswerback.domain.users;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
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
    DeptClassRepository deptClassRepository;

    @Autowired
    UsersRepository usersRepository;

    @AfterEach
    public void cleanup() {
        usersRepository.deleteAll();
        deptClassRepository.deleteAll();
    }

    @Test
    public void 신규유저_저장_및_불러오기() {
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

        usersRepository.save(Users.builder()
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
                .build());

        // 비교를 위한 기준 시간 변수 초기화
        LocalDateTime time = LocalDateTime.of(2019, 11, 25, 0, 0, 0);

        //when
        List<Users> usersList = usersRepository.findAll();

        //then
        Users users = usersList.get(0);
        assertThat(users.getDeptClass().getDeptId()).isEqualTo(deptClass.getDeptId());
        assertThat(users.getNickname()).isEqualTo(nickname);
        assertThat(users.getDeptName()).isEqualTo(deptName);
        assertThat(users.getUniv()).isEqualTo(univ);
        assertThat(users.getEntranceYear()).isEqualTo(entranceYear);
        assertThat(users.getOauthId()).isEqualTo(oauthId);
        assertThat(users.getRefreshToken()).isEqualTo(refreshToken);
        assertThat(users.getIsDelete()).isEqualTo(isDelete);
        assertThat(users.getAgreeTerms()).isEqualTo(agreeTerms);
        assertThat(users.getIsCertified()).isEqualTo(isCertified);

        assertThat(users.getCreatedAt()).isAfter(time);
        assertThat(users.getUpdatedAt()).isAfter(time);
    }
}