package com.fineapple.everyanswerback.web.blockUserLog;

import com.fineapple.everyanswerback.service.BlockUserLogService;
import com.fineapple.everyanswerback.web.blockUserLog.dto.BlockUserLogSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BlockUserLog", description = "차단 유저 로그 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/blockUserLog")
public class BlockUserLogApiController {

    private final BlockUserLogService blockUserLogService;

    @Operation(summary = "유저 차단하기")
    @PostMapping()
    public Long save(@RequestBody BlockUserLogSaveRequestDto requestDto) {
        return blockUserLogService.save(requestDto);
    }

    @Operation(summary = "유저 차단 해제하기")
    @DeleteMapping()
    public void delete(@RequestParam Long userId, @RequestParam Long blockUserId) {
        blockUserLogService.delete(userId, blockUserId);
    }
}
