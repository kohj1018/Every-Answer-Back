package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassResponseDto;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DeptClassService {
    private final DeptClassRepository deptClassRepository;

    @Transactional
    public Long save(DeptClassSaveRequestDto requestDto) {
        return deptClassRepository.save(requestDto.toEntity()).getDeptId();
    }

    public List<DeptClassResponseDto> getAll() {
        List<DeptClass> entityList = deptClassRepository.getAll();

        List<DeptClassResponseDto> responseDtoList = new ArrayList<>();

        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                responseDtoList.add(new DeptClassResponseDto(entity));
            });
        }

        return responseDtoList;
    }
}
