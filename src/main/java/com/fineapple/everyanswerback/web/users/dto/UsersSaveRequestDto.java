package com.fineapple.everyanswerback.web.users.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersSaveRequestDto {
    private Long deptId;
    private String nickname;
    private String deptName;
    private String univ;
    private int entranceYear;
    private String oauthId;
    private String refreshToken;
    private Boolean isDelete;

    @Builder
    public UsersSaveRequestDto(Long deptId, String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, Boolean isDelete) {
        this.deptId = deptId;
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.isDelete = isDelete;
    }

    public Users toEntity(DeptClass deptClass) {
        return Users.builder()
                .deptClass(deptClass)
                .nickname(nickname)
                .deptName(deptName)
                .univ(univ)
                .entranceYear(entranceYear)
                .oauthId(oauthId)
                .refreshToken(refreshToken)
                .isDelete(isDelete)
                .build();
    }
}
