package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.blockUserLog.BlockUserLog;
import com.fineapple.everyanswerback.domain.blockUserLog.BlockUserLogRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.blockUserLog.dto.BlockUserLogSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BlockUserLogService {
    private final BlockUserLogRepository blockUserLogRepository;
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(BlockUserLogSaveRequestDto requestDto) {
        Optional<BlockUserLog> log = blockUserLogRepository.findByUserIdAndBlockUserId(requestDto.getUserId(), requestDto.getBlockUserId());

        if (log.isPresent()) {  // 이미 차단한 기록이 있다면,
            throw new IllegalArgumentException("이미 차단했습니다.");
        }

        Users user = usersRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + requestDto.getUserId()));

        return blockUserLogRepository.save(requestDto.toEntity(user, requestDto.getBlockUserId())).getBlockUserLogId();
    }

    @Transactional
    public void delete(Long userId, Long blockUserId) {
        Optional<BlockUserLog> log = blockUserLogRepository.findByUserIdAndBlockUserId(userId, blockUserId);

        if (log.isPresent()) {
            blockUserLogRepository.deleteById(log.get().getBlockUserLogId());
        } else {
            throw new IllegalArgumentException("차단 기록이 존재하지 않습니다.");
        }
    }
}
