package com.fineapple.everyanswerback.web.users;

import com.fineapple.everyanswerback.service.UsersService;
import com.fineapple.everyanswerback.web.users.dto.UsersResponseDto;
import com.fineapple.everyanswerback.web.users.dto.UsersSaveRequestDto;
import com.fineapple.everyanswerback.web.users.dto.UsersUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "유저 관련 api 입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UsersApiController {

    private final UsersService usersService;

    @Operation(summary = "새 유저 추가하기")
    @PostMapping()
    public Long save(@RequestBody UsersSaveRequestDto requestDto) {
        return usersService.save(requestDto);
    }

    @Operation(summary = "유저 정보 수정하기")
    @PutMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody UsersUpdateRequestDto requestDto) {
        return usersService.update(id, requestDto);
    }

    @Operation(summary = "ID로 유저 정보 불러오기")
    @GetMapping("/{id}")
    public UsersResponseDto findById(@PathVariable Long id) {
        return usersService.findById(id);
    }

    @Operation(summary = "OauthId로 유저 Id 불러오기")
    @GetMapping("/oauth/{oauthId}")
    public Long findByOauthId(@PathVariable String oauthId) {
        return usersService.findByOauthId(oauthId);
    }

    @Operation(summary = "nickname 중복확인")
    @GetMapping("/nicknameCheck/{nickname}")
    public boolean findByNickname(@PathVariable String nickname) {
        return usersService.findByNickname(nickname);
    }
}
