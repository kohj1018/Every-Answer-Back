package com.fineapple.everyanswerback.domain.answerPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerPostsRepository extends JpaRepository<AnswerPosts, Long> {

    @Query(value = "SELECT ap FROM answer_posts ap WHERE ap.questionPost.questionPostId = ?1 ORDER BY ap.likeNum DESC, ap.answerPostId DESC")
    List<AnswerPosts> findByQuestionPostId(Long questionPostId);

    @Query(value = "SELECT ap FROM answer_posts ap WHERE ap.user.userId = ?1 ORDER BY ap.createdAt DESC")
    List<AnswerPosts> findByUserId(Long userId);
}
