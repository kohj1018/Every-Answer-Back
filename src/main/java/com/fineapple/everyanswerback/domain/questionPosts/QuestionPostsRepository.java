package com.fineapple.everyanswerback.domain.questionPosts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionPostsRepository extends JpaRepository<QuestionPosts, Long> {

//    @Query("SELECT qp FROM question_posts qp WHERE qp.questionPostId < ?1 ORDER BY qp.questionPostId DESC LIMIT 21")
//    public List<QuestionPosts> getPostsLowerThanId(Long lastPostId);

    @Query("SELECT qp FROM question_posts qp WHERE qp.questionPostId < ?1 ORDER BY qp.questionPostId DESC")
    Page<QuestionPosts> findByquestionPostIdLessThanOrderByquestionPostIdDesc(Long lastPostId, PageRequest pageRequest);

    List<QuestionPosts> findByTitleContainingOrContentContainingOrderByQuestionPostIdDesc(String searchTerm1, String searchTerm2);

    @Query(value = "SELECT qp FROM question_posts qp WHERE qp.user.userId = ?1 ORDER BY qp.createdAt DESC")
    List<QuestionPosts> findByUserId(Long userId);
}
