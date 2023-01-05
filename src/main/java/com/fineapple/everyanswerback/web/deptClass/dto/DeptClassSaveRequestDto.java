package com.fineapple.everyanswerback.web.deptClass.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeptClassSaveRequestDto {
    private Long deptId;
    private String college;
    private String name;

    @Builder
    public DeptClassSaveRequestDto(Long deptId, String college, String name) {
        this.deptId = deptId;
        this.college = college;
        this.name = name;
    }

    public DeptClass toEntity() {
        return DeptClass.builder()
                .deptId(deptId)
                .college(college)
                .name(name)
                .build();
    }
}
