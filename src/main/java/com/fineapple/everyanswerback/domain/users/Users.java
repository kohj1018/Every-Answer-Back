package com.fineapple.everyanswerback.domain.users;

import com.fineapple.everyanswerback.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String nickname;

    @Column(name = "dept_name", length = 30, nullable = false)
    private String deptName;

    @Column(length = 20, nullable = false)
    private String univ;

    @Column(name = "entrance_year", nullable = false)
    private int entranceYear;

    @Column(name = "oauth_id", nullable = false)
    private String oauthId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Builder
    public Users(String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, boolean isActive) {
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.isActive = isActive;
    }
}
