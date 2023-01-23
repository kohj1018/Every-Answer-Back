package com.fineapple.everyanswerback.web.questionPosts;

import com.fineapple.everyanswerback.service.QuestionPostsService;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsResponseDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsSaveRequestDto;
import com.fineapple.everyanswerback.web.questionPosts.dto.QuestionPostsUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "QuestionPosts", description = "질문글 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/questionPosts")
public class QuestionPostsApiController {

    private final QuestionPostsService questionPostsService;

    @Operation(summary = "질문글 저장하기")
    @PostMapping()
    public Long save(@RequestBody QuestionPostsSaveRequestDto requestDto) {
        return questionPostsService.save(requestDto);
    }

    @Operation(summary = "질문글 수정하기")
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody QuestionPostsUpdateRequestDto requestDto) {
        return questionPostsService.update(id, requestDto);
    }

    @Operation(summary = "ID로 질문글 불러오기")
    @GetMapping("/{id}")
    public QuestionPostsResponseDto findById(@PathVariable Long id) {
        return questionPostsService.findById(id);
    }

    @Operation(summary = "질문글 무한스크롤")
    @GetMapping()
    public List<QuestionPostsResponseDto> getPostsLowerThanId(@RequestParam Long lastPostId, @RequestParam int size) {
//        return questionPostsService.getPostsLowerThanId(lastPostId);
        return questionPostsService.fetchPostPagesBy(lastPostId, size);
    }

    @Operation(summary = "질문글 검색하기")
    @GetMapping("/search/{searchTerm}")
    public List<QuestionPostsResponseDto> getSearchedPost(@PathVariable String searchTerm, @RequestParam Long lastPostId, @RequestParam int size) {
        return questionPostsService.findByTitleContainingOrContentContainingAndQuestionPostIdLessThanOrderByQuestionPostIdDesc(searchTerm, lastPostId, size);
    }
}
