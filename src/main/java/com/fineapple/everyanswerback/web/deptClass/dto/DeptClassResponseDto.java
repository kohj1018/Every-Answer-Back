package com.fineapple.everyanswerback.web.deptClass.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import lombok.Getter;

@Getter
public class DeptClassResponseDto {

    private Long deptId;
    private String college;
    private String name;

    public DeptClassResponseDto(DeptClass entity) {
        this.deptId = entity.getDeptId();
        this.college = entity.getCollege();
        this.name = entity.getName();
    }
}
