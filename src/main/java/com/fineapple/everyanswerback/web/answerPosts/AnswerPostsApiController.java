package com.fineapple.everyanswerback.web.answerPosts;

import com.fineapple.everyanswerback.service.AnswerPostsService;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.answerPosts.dto.AnswerPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AnswerPostsApiController {

    private final AnswerPostsService answerPostsService;

    @PostMapping("/api/v1/answerPosts")
    public Long save(@RequestBody AnswerPostsSaveRequestDto requestDto) {
        return answerPostsService.save(requestDto);
    }

    @PutMapping("/api/v1/answerPosts/{id}")
    public Long update(@PathVariable Long id, @RequestBody AnswerPostsUpdateRequestDto requestDto) {
        return answerPostsService.update(id, requestDto);
    }

    // TODO: questionPosts 에 단 answerPosts 들 한꺼번에 받을 수 있게하는 api 구현하기
//    @GetMapping("/api/v1/answerPosts/")
}
