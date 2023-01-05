package com.fineapple.everyanswerback.web.deptClass;

import com.fineapple.everyanswerback.service.DeptClassService;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class DeptClassApiController {

    private final DeptClassService deptClassService;

    @PostMapping("/api/v1/deptClass")
    public Long save(@RequestBody DeptClassSaveRequestDto requestDto) {
        return deptClassService.save(requestDto);
    }
}
