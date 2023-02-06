package com.fineapple.everyanswerback.web.deptClass;

import com.fineapple.everyanswerback.service.DeptClassService;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassResponseDto;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "DeptClass", description = "전공분류 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/deptClass")
public class DeptClassApiController {

    private final DeptClassService deptClassService;

    @Operation(summary = "새 전공분류 추가하기")
    @PostMapping()
    public Long save(@RequestBody DeptClassSaveRequestDto requestDto) {
        return deptClassService.save(requestDto);
    }

    @Operation(summary = "모든 전공 분류 불러오기")
    @GetMapping("/getAll")
    public List<DeptClassResponseDto> getAll() {
        return deptClassService.getAll();
    }
}
