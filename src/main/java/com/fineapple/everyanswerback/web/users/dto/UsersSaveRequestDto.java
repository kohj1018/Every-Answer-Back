package com.fineapple.everyanswerback.web.users.dto;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.users.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UsersSaveRequestDto {
    private DeptClass deptClass;
    private String nickname;
    private String deptName;
    private String univ;
    private int entranceYear;
    private String oauthId;
    private String refreshToken;
    private Boolean isDelete;

    @Builder
    public UsersSaveRequestDto(DeptClass deptClass, String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, Boolean isDelete) {
        this.deptClass = deptClass;
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.isDelete = isDelete;
    }

    public Users toEntity() {
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
