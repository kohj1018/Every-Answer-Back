package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeptClassService {
    private final DeptClassRepository deptClassRepository;

    @Transactional
    public Long save(DeptClassSaveRequestDto requestDto) {
        return deptClassRepository.save(requestDto.toEntity()).getDeptId();
    }
}
