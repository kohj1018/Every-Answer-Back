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
    private Long deptId;
    private String nickname;
    private String deptName;
    private String univ;
    private int entranceYear;
    private String oauthId;
    private String refreshToken;
    private Boolean isDelete;

    public UsersUpdateRequestDto(Users entity) {
        this.deptId = entity.getDeptClass().getDeptId();
        this.nickname = entity.getNickname();
        this.deptName = entity.getDeptName();
        this.univ = entity.getUniv();
        this.entranceYear = entity.getEntranceYear();
        this.oauthId = entity.getOauthId();
        this.refreshToken = entity.getRefreshToken();
        this.isDelete = entity.getIsDelete();
    }

    @Builder
    public UsersUpdateRequestDto(Long deptId, String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, Boolean isDelete) {
        this.deptId = deptId;
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.isDelete = isDelete;
    }
}
