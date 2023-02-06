package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsResponseDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AnswerPostsService {
    private final AnswerPostsRepository answerPostsRepository;

    private final QuestionPostsRepository questionPostsRepository;

    private final UsersRepository usersRepository;

    @Transactional
    public Long save(AnswerPostsSaveRequestDto requestDto) {
        QuestionPosts questionPost = questionPostsRepository.findById(requestDto.getQuestionPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + requestDto.getQuestionPostId()));

        Users user = usersRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + requestDto.getUserId()));

        if (Objects.equals(questionPost.getUser().getUserId(), user.getUserId())) {
            throw new IllegalArgumentException("질문글 작성자는 답변글을 작성할 수 없습니다!");
        }

        return answerPostsRepository.save(requestDto.toEntity(questionPost, user)).getAnswerPostId();
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

    public List<AnswerPostsResponseDto> findByUserId(Long userId) {
        List<AnswerPosts> entityList = answerPostsRepository.findByUserId(userId);

        List<AnswerPostsResponseDto> responseDtoList = new ArrayList<>();

        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                responseDtoList.add(new AnswerPostsResponseDto(entity));
            });
        }

        return responseDtoList;
    }
}
