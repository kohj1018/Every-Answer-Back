package com.fineapple.everyanswerback.web.questionPosts;

import com.fineapple.everyanswerback.service.QuestionPostsService;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsResponseDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class QuestionPostsApiController {

    private final QuestionPostsService questionPostsService;

    @PostMapping("/api/v1/questionPosts")
    public Long save(@RequestBody QuestionPostsSaveRequestDto requestDto) {
        return questionPostsService.save(requestDto);
    }

    @PutMapping("/api/v1/questionPosts/{id}")
    public Long update(@PathVariable Long id, @RequestBody QuestionPostsUpdateRequestDto requestDto) {
        return questionPostsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/questionPosts/{id}")
    public QuestionPostsResponseDto findById(@PathVariable Long id) {
        return questionPostsService.findById(id);
    }
}
