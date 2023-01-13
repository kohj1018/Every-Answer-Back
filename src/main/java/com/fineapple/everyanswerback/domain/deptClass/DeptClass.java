package com.fineapple.everyanswerback.domain.deptClass;

import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DeptClass {

    @Id
    @Column(name = "dept_id")
    private Long deptId;

    @Column(length = 30, nullable = false)
    private String college;

    @Column(length = 30, nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deptClass")  // Users 와의 양방향 매핑을 위해 추가
    private List<Users> usersList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "deptClass")  // QuestionPosts 와의 양방향 매핑을 위해 추가
    private List<QuestionPosts> questionPostsList = new ArrayList<>();

    @Builder
    public DeptClass(Long deptId, String college, String name) {
        this.deptId = deptId;
        this.college = college;
        this.name = name;
    }
}
