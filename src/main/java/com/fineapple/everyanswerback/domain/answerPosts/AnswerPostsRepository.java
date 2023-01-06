package com.fineapple.everyanswerback.domain.answerPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerPostsRepository extends JpaRepository<AnswerPosts, Long> {

    @Query(value = "SELECT ap FROM answer_posts ap WHERE ap.questionPost.questionPostId = ?1")
    List<AnswerPosts> findByQuestionPostId(Long questionPostId);
}
