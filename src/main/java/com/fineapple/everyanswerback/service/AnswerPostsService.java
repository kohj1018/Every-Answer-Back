package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsResponseDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // TODO: questionPosts 에 단 answerPosts 들 한꺼번에 받을 수 있게하는 함수 구현하기
    public List<AnswerPostsResponseDto> findByQuestionPostsId(Long id) {
        return null;
    }
}
