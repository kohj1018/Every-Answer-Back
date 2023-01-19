package com.fineapple.everyanswerback.domain.likeLogAnswerPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeLogAnswerPostsRepository extends JpaRepository<LikeLogAnswerPosts, Long> {

    @Query(value = "SELECT l FROM likeLog_answer_posts l WHERE l.answerPost.answerPostId = ?1 AND l.user.userId = ?2")
    Optional<LikeLogAnswerPosts> findByAnswerPostIdAndUserId(Long answerPostId, Long userId);
}
