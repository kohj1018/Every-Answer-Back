package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsResponseDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class QuestionPostsService {
    private final QuestionPostsRepository questionPostsRepository;

    @Transactional
    public Long save(QuestionPostsSaveRequestDto requestDto) {
        return questionPostsRepository.save(requestDto.toEntity()).getQuestionPostId();
    }

    @Transactional
    public Long update(Long id, QuestionPostsUpdateRequestDto requestDto) {
        QuestionPosts questionPost = questionPostsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        questionPost.update(requestDto);

        return id;
    }

    public QuestionPostsResponseDto findById(Long id) {
        QuestionPosts entity = questionPostsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        return new QuestionPostsResponseDto(entity);
    }
}
