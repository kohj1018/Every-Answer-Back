package com.fineapple.everyanswerback.web.users.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UsersUpdateRequestDto {
    private DeptClass deptClass;
    private String nickname;
    private String deptName;
    private String univ;
    private int entranceYear;
    private String oauthId;
    private String refreshToken;
    private LocalDateTime updatedAt;
    private Boolean isDelete;

    public UsersUpdateRequestDto(Users entity) {
        this.deptClass = entity.getDeptClass();
        this.nickname = entity.getNickname();
        this.deptName = entity.getDeptName();
        this.univ = entity.getUniv();
        this.entranceYear = entity.getEntranceYear();
        this.oauthId = entity.getOauthId();
        this.refreshToken = entity.getRefreshToken();
        this.isDelete = entity.getIsDelete();
    }

    @Builder
    public UsersUpdateRequestDto(DeptClass deptClass, String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, LocalDateTime updatedAt, Boolean isDelete) {
        this.deptClass = deptClass;
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.updatedAt = updatedAt;
        this.isDelete = isDelete;
    }
}
