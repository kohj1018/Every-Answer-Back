package com.fineapple.everyanswerback.domain.users;

import com.fineapple.everyanswerback.domain.BaseTimeEntity;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.blockUserLog.BlockUserLog;
import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.web.users.dto.UsersUpdateRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id")
    private DeptClass deptClass;

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

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Column(name = "agree_terms", nullable = false) // 약관동의 여부
    private Boolean agreeTerms;

    @Column(name = "is_certified", nullable = false)    // 학교인증 여부
    private Boolean isCertified;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")  // QuestionPosts 와의 양방향 매핑을 위해 추가
    private List<QuestionPosts> questionPostsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")   // AnswerPosts 와의 양방향 매핑을 위해 추가
    private List<AnswerPosts> answerPostsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")   // LikeLogAnswerPosts 와의 양방향 매핑을 위해 추가
    private List<LikeLogAnswerPosts> likeLogAnswerPostsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")   // BlockUserLog 와의 양방향 매핑을 위해 추가
    private List<BlockUserLog> blockUserLogList = new ArrayList<>();

    @Builder
    public Users(DeptClass deptClass, String nickname, String deptName, String univ, int entranceYear, String oauthId, String refreshToken, Boolean isDelete, Boolean agreeTerms, Boolean isCertified) {
        this.deptClass = deptClass;
        this.nickname = nickname;
        this.deptName = deptName;
        this.univ = univ;
        this.entranceYear = entranceYear;
        this.oauthId = oauthId;
        this.refreshToken = refreshToken;
        this.isDelete = isDelete;
        this.agreeTerms = agreeTerms;
        this.isCertified = isCertified;
    }

    public void update(UsersUpdateRequestDto requestDto, DeptClass deptClass) {
        this.deptClass = deptClass;
        this.nickname = requestDto.getNickname();
        this.deptName = requestDto.getDeptName();
        this.univ = requestDto.getUniv();
        this.entranceYear = requestDto.getEntranceYear();
        this.oauthId = requestDto.getOauthId();
        this.refreshToken = requestDto.getRefreshToken();
        this.isDelete = requestDto.getIsDelete();
        this.agreeTerms = requestDto.getAgreeTerms();
        this.isCertified = requestDto.getIsCertified();
    }
}
