package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPosts;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPostsRepository;
import com.fineapple.everyanswerback.web.likeLogAnswerPosts.dto.LikeLogAnswerPostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeLogAnswerPostsService {
    private final LikeLogAnswerPostsRepository likeLogAnswerPostsRepository;

    @Transactional
    public Long save(LikeLogAnswerPostsSaveRequestDto requestDto) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdANDUserId(requestDto.getAnswerPost().getAnswerPostId(), requestDto.getUser().getUserId());

        if (log.isPresent()) {  // 이미 추천을 누른 기록이 있다면,
            throw new IllegalArgumentException("이미 추천을 눌렀습니다.");
        }

        return likeLogAnswerPostsRepository.save(requestDto.toEntity()).getLikeLogAnswerPostsId();
    }

    @Transactional
    public void delete(Long userId, Long answerPostId) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdANDUserId(userId, answerPostId);

        if (log.isPresent()) {
            likeLogAnswerPostsRepository.deleteById(log.get().getLikeLogAnswerPostsId());
        }
    }
}
