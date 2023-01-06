package com.fineapple.everyanswerback.web.users.dto;

import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UsersResponseDto {

    private Long userId;
    private DeptClassResponseDto deptClass;
    private String nickname;
    private String deptName;
    private String univ;
    private int entranceYear;
    private String oauthId;
    private String refreshToken;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDelete;

    public UsersResponseDto(Users entity) {
        this.userId = entity.getUserId();
        this.deptClass = new DeptClassResponseDto(entity.getDeptClass());
        this.nickname = entity.getNickname();
        this.deptName = entity.getDeptName();
        this.univ = entity.getUniv();
        this.entranceYear = entity.getEntranceYear();
        this.oauthId = entity.getOauthId();
        this.refreshToken = entity.getRefreshToken();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.isDelete = entity.getIsDelete();
    }
}
