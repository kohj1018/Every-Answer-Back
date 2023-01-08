package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPosts;
import com.fineapple.everyanswerback.domain.questionPosts.QuestionPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
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

    private final UsersRepository usersRepository;  // 추가

    private final DeptClassRepository deptClassRepository;  // 추가

    @Transactional
    public Long save(QuestionPostsSaveRequestDto requestDto) {
        Users user = usersRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + requestDto.getUserId()));

        DeptClass deptClass = deptClassRepository.findById(requestDto.getDeptClassId())
                .orElseThrow(() -> new IllegalArgumentException("해당 전공분류가 존재하지 않습니다. id=" + requestDto.getDeptClassId()));

        return questionPostsRepository.save(requestDto.toEntity(user, deptClass)).getQuestionPostId();
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
