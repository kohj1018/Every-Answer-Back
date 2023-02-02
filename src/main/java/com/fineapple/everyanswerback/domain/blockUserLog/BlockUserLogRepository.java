package com.fineapple.everyanswerback.domain.blockUserLog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BlockUserLogRepository extends JpaRepository<BlockUserLog, Long> {

    @Query(value = "SELECT l FROM block_user_log l WHERE l.user.userId = ?1 AND l.blockUserId = ?2")
    Optional<BlockUserLog> findByUserIdAndBlockUserId(Long userId, Long blockUserId);
}
