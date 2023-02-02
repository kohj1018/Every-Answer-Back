package com.fineapple.everyanswerback.web.blockUserLog.dto;

import com.fineapple.everyanswerback.domain.blockUserLog.BlockUserLog;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlockUserLogSaveRequestDto {
    private Long userId;
    private Long blockUserId;

    @Builder
    public BlockUserLogSaveRequestDto(Long userId, Long blockUserId) {
        this.userId = userId;
        this.blockUserId = blockUserId;
    }

    public BlockUserLog toEntity(Users user, Long blockUserId) {
        return BlockUserLog.builder()
                .user(user)
                .blockUserId(blockUserId)
                .build();
    }
}
