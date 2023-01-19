package com.fineapple.everyanswerback.web.likeLogAnswerPosts;

import com.fineapple.everyanswerback.service.LikeLogAnswerPostsService;
import com.fineapple.everyanswerback.web.likeLogAnswerPosts.dto.LikeLogAnswerPostsSaveRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "LikeLog-AnswerPosts", description = "질문글 추천 기록 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/likeLogAnswerPosts")
public class LikeLogAnswerPostsApiController {

    private final LikeLogAnswerPostsService likeLogAnswerPostsService;

    @Operation(summary = "추천 누르기 (추천 기록 추가하기)")
    @PostMapping()
    public Long save(@RequestBody LikeLogAnswerPostsSaveRequestDto requestDto) {
        return likeLogAnswerPostsService.save(requestDto);
    }

    @Operation(summary = "추천 취소하기 (추천 기록 제거하기)")
    @DeleteMapping()
    public void delete(@RequestParam Long answerPostId, @RequestParam Long userId) {
        likeLogAnswerPostsService.delete(answerPostId, userId);
    }

    @Operation(summary = "사용자가 추천을 눌렀는지 확인하기")
    @GetMapping()
    public boolean getLikeLogByUserId(@RequestParam Long answerPostId, @RequestParam Long userId) {
        return likeLogAnswerPostsService.findByAnswerPostIdAndUserId(answerPostId, userId);
    }
}
