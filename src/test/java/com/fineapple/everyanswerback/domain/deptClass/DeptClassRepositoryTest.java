package com.fineapple.everyanswerback.domain.deptClass;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DeptClassRepositoryTest {

    @Autowired
    DeptClassRepository deptClassRepository;

    @AfterEach
    public void cleanup() {
        deptClassRepository.deleteAll();
    }

    @Test
    public void 전공분류_저장_및_불러오기() {
        //given
        Long deptId = 100L;
        String college = "소융대";
        String name = "소프트웨어";

        deptClassRepository.save(DeptClass.builder()
                .deptId(deptId)
                .college(college)
                .name(name)
                .build());

        //when
        List<DeptClass> deptClassList = deptClassRepository.findAll();

        //then
        DeptClass deptClass = deptClassList.get(0);
        assertThat(deptClass.getDeptId()).isEqualTo(deptId);
        assertThat(deptClass.getCollege()).isEqualTo(college);
        assertThat(deptClass.getName()).isEqualTo(name);
    }
}