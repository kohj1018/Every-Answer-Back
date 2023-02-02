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

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeLogAnswerPostsService {
    private final LikeLogAnswerPostsRepository likeLogAnswerPostsRepository;
    private final UsersRepository usersRepository;
    private final AnswerPostsRepository answerPostsRepository;

    @Transactional
    public Long save(LikeLogAnswerPostsSaveRequestDto requestDto) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdAndUserId(requestDto.getAnswerPostId(), requestDto.getUserId());

        if (log.isPresent()) {  // 이미 추천을 누른 기록이 있다면,
            throw new IllegalArgumentException("이미 추천을 눌렀습니다.");
        }

        Users user = usersRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + requestDto.getUserId()));

        AnswerPosts answerPost = answerPostsRepository.findById(requestDto.getAnswerPostId())
                .orElseThrow(() -> new IllegalArgumentException("해당 답변글이 존재하지 않습니다. id=" + requestDto.getAnswerPostId()));

        if (Objects.equals(answerPost.getUser().getUserId(), user.getUserId())) {   // 만약 답변글 작성자와 추천 누르는 사람이 같은 사람이라면 -> 에러
            throw new IllegalArgumentException("자신의 글은 추천할 수 없습니다.");
        }

        return likeLogAnswerPostsRepository.save(requestDto.toEntity(user, answerPost)).getLikeLogAnswerPostsId();
    }

    @Transactional
    public void delete(Long answerPostId, Long userId) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdAndUserId(answerPostId, userId);

        if (log.isPresent()) {
            likeLogAnswerPostsRepository.deleteById(log.get().getLikeLogAnswerPostsId());
        } else {
            throw new IllegalArgumentException("추천 기록이 존재하지 않습니다.");
        }
    }

    public boolean findByAnswerPostIdAndUserId(Long answerPostId, Long userId) {
        Optional<LikeLogAnswerPosts> log = likeLogAnswerPostsRepository.findByAnswerPostIdAndUserId(answerPostId, userId);

        return log.isPresent(); // log 존재 => true, log 존재 x => false
    }
}
