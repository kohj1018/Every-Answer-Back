package com.fineapple.everyanswerback.web.answerPosts;

import com.fineapple.everyanswerback.service.AnswerPostsService;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsResponseDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "AnswerPosts", description = "답변글 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/answerPosts")
public class AnswerPostsApiController {

    private final AnswerPostsService answerPostsService;

    @Operation(summary = "답변글 저장하기")
    @PostMapping()
    public Long save(@RequestBody AnswerPostsSaveRequestDto requestDto) {
        return answerPostsService.save(requestDto);
    }

    @Operation(summary = "답변글 수정하기")
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody AnswerPostsUpdateRequestDto requestDto) {
        return answerPostsService.update(id, requestDto);
    }

    @Operation(summary = "질문글 ID에 달린 답변글 모두 불러오기")
    @GetMapping()
    public List<AnswerPostsResponseDto> findByQuestionPostId(@RequestParam Long questionPostId) {
        return answerPostsService.findByQuestionPostId(questionPostId);
    }
}
