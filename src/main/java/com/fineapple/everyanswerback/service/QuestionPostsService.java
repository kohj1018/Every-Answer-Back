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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

//        DeptClass deptClass = deptClassRepository.findById(requestDto.getDeptId())
//                .orElseThrow(() -> new IllegalArgumentException("해당 전공분류가 존재하지 않습니다. id=" + requestDto.getDeptId()));

        // TODO: 저장할 때 다른 전공 질문도 할 수 있게 따로 저장할 예정이나, 현재는 작성자의 전공분류대로 저장되도록 함. (위에는 주석처리)
        DeptClass deptClass = deptClassRepository.findById(user.getDeptClass().getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("해당 전공분류가 존재하지 않습니다. id=" + user.getDeptClass().getDeptId()));

        return questionPostsRepository.save(requestDto.toEntity(user, deptClass)).getQuestionPostId();
    }

    @Transactional
    public Long update(Long id, QuestionPostsUpdateRequestDto requestDto) {
        QuestionPosts questionPost = questionPostsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        DeptClass deptClass = deptClassRepository.findById(requestDto.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("해당 전공분류가 존재하지 않습니다. id=" + requestDto.getDeptId()));

        questionPost.update(requestDto, deptClass);

        return id;
    }

    public QuestionPostsResponseDto findById(Long id) {
        QuestionPosts entity = questionPostsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다. id=" + id));

        return new QuestionPostsResponseDto(entity);
    }

//    public List<QuestionPostsResponseDto> getPostsLowerThanId(Long lastPostId) {
//        List<QuestionPosts> entityList = questionPostsRepository.getPostsLowerThanId(lastPostId);
//
//        List<QuestionPostsResponseDto> responseDtoList = new ArrayList<>();
//
//        if (entityList != null && !entityList.isEmpty()) {
//            entityList.forEach(entity -> {
//                responseDtoList.add(new QuestionPostsResponseDto(entity));
//            });
//        }
//
//        return responseDtoList;
//    }

    public List<QuestionPostsResponseDto> fetchPostPagesBy(Long lastPostId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<QuestionPosts> entityPage = questionPostsRepository.findByquestionPostIdLessThanOrderByquestionPostIdDesc(lastPostId, pageRequest);
        List<QuestionPosts> entityList = entityPage.getContent();

        List<QuestionPostsResponseDto> responseDtoList = new ArrayList<>();

        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                responseDtoList.add(new QuestionPostsResponseDto(entity));
            });
        }

        return responseDtoList;
    }

    public List<QuestionPostsResponseDto> findByTitleContainingOrContentContainingAndQuestionPostIdLessThanOrderByQuestionPostIdDesc(String searchTerm, Long lastPostId, int size) {
        PageRequest pageRequest = PageRequest.of(0, size);
        Page<QuestionPosts> entityPage = questionPostsRepository.findByTitleContainingOrContentContainingAndQuestionPostIdLessThanOrderByQuestionPostIdDesc(searchTerm, searchTerm, lastPostId, pageRequest);
        List<QuestionPosts> entityList = entityPage.getContent();

        List<QuestionPostsResponseDto> responseDtoList = new ArrayList<>();

        if (entityList != null && !entityList.isEmpty()) {
            entityList.forEach(entity -> {
                responseDtoList.add(new QuestionPostsResponseDto(entity));
            });
        }

        return responseDtoList;
    }
}
