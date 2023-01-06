package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsResponseDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AnswerPostsService {
    private final AnswerPostsRepository answerPostsRepository;

    @Transactional
    public Long save(AnswerPostsSaveRequestDto requestDto) {
        return answerPostsRepository.save(requestDto.toEntity()).getAnswerPostId();
    }

    @Transactional
    public Long update(Long id, AnswerPostsUpdateRequestDto requestDto) {
        AnswerPosts answerPost = answerPostsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 답변글이 존재하지 않습니다. id=" + id));

        answerPost.update(requestDto);

        return id;
    }

    public List<AnswerPostsResponseDto> findByQuestionPostId(Long questionPostId) {
        List<AnswerPosts> entityList = answerPostsRepository.findByQuestionPostId(questionPostId);

        List<AnswerPostsResponseDto> responseDtoList = new ArrayList<>();

        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                responseDtoList.add(new AnswerPostsResponseDto(entity));
            });
        }

        return responseDtoList;
    }
}
