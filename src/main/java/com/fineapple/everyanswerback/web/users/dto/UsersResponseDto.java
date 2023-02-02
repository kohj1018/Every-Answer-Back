package com.fineapple.everyanswerback.web.users.dto;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.blockUserLog.BlockUserLog;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsResponseDto;
import com.fineapple.everyanswerback.web.deptClass.dto.DeptClassResponseDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private Boolean agreeTerms;
    private Boolean isCertified;
    private List<Long> questionPostsList;
    private List<Long> answerPostsList;
    private List<Long> likeAnswerPostsList;
    private List<Long> blockUserIdList;

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
        this.agreeTerms = entity.getAgreeTerms();
        this.isCertified = entity.getIsCertified();
        this.questionPostsList = entity.getQuestionPostsList().stream()
                .map(QuestionPosts::getQuestionPostId)
                .collect(Collectors.toList());
        this.answerPostsList = entity.getAnswerPostsList().stream()
                .map(AnswerPosts::getAnswerPostId)
                .collect(Collectors.toList());
        this.likeAnswerPostsList = entity.getLikeLogAnswerPostsList().stream()
                .map(likeLogAnswerPosts -> likeLogAnswerPosts.getAnswerPost().getAnswerPostId())
                .collect(Collectors.toList());
        this.blockUserIdList = entity.getBlockUserLogList().stream()
                .map(BlockUserLog::getBlockUserId)
                .collect(Collectors.toList());
    }
}
