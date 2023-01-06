package com.fineapple.everyanswerback.domain.answerPosts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerPostsRepository extends JpaRepository<AnswerPosts, Long> {

    // TODO: WHERE 부분 이렇게 해도 되는건지 확인 필요
    @Query("SELECT ap FROM answer_posts ap WHERE ap.questionPost = :questionPostId")
    List<AnswerPosts> findByQuestionPostId(@Param("questionPostId") Long questionPostId);
}
