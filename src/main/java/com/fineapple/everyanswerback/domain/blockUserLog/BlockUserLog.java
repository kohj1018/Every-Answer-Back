package com.fineapple.everyanswerback.domain.blockUserLog;

import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "block_user_log")
public class BlockUserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_user_log_id")
    private Long blockUserLogId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "block_user_id")
    private Long blockUserId;

    @Builder
    public BlockUserLog(Users user, Long blockUserId) {
        this.user = user;
        this.blockUserId = blockUserId;
    }
}
