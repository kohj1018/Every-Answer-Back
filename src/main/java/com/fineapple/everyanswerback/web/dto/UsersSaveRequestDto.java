package com.fineapple.everyanswerback.web.dto;

import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UsersSaveRequestDto {
    private String nickname;
    private String deptName;
    private String univ;
    private int entranceYear;
    private String oauthId;
    private String refreshToken;
    private boolean isActive;

    @Builder
    public UsersSaveRequestDto(String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, boolean isActive) {
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.isActive = isActive;
    }

    public Users toEntity() {
        return Users.builder()
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isActive(isActive)
                .build();
    }
}
