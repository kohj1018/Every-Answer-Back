package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.answerPosts.AnswerPosts;
import com.fineapple.everyanswerback.domain.answerPosts.AnswerPostsRepository;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPosts;
import com.fineapple.everyanswerback.domain.likeLogAnswerPosts.LikeLogAnswerPostsRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.likeLogAnswerPosts.dto.LikeLogAnswerPostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeLogAnswerPostsService {
    private final LikeLogAnswerPostsRepository likeLogAnswerPostsRepository;
    private final UsersRepository usersRepository;
    private final AnswerPostsRepository answerPostsRepository;

    @Transactional
    public Long save(LikeLogAnswerPostsSaveRequestDto requestDto) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdANDUserId(requestDto.getAnswerPostId(), requestDto.getUserId());

        Users user = usersRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + requestDto.getUserId()));

        AnswerPosts answerPost = answerPostsRepository.findById(requestDto.getAnswerPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 답변글이 존재하지 않습니다. id=" + requestDto.getAnswerPostId()));

        if (log.isPresent()) {  // 이미 추천을 누른 기록이 있다면,
            throw new IllegalArgumentException("이미 추천을 눌렀습니다.");
        }

        return likeLogAnswerPostsRepository.save(requestDto.toEntity(user, answerPost)).getLikeLogAnswerPostsId();
    }

    @Transactional
    public void delete(Long userId, Long answerPostId) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdANDUserId(userId, answerPostId);

        if (log.isPresent()) {
            likeLogAnswerPostsRepository.deleteById(log.get().getLikeLogAnswerPostsId());
        }
    }
}
