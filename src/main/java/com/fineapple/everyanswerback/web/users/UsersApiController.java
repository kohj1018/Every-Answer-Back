package com.fineapple.everyanswerback.web.users;

import com.fineapple.everyanswerback.service.UsersService;
import com.fineapple.everyanswerback.web.users.dto.OtherUsersResponseDto;
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

    @Operation(summary = "유저 정보 수정하기 (oauthId 로만 가능)")
    @PutMapping("/{oauthId}")
    public String update(@PathVariable String oauthId, @RequestBody UsersUpdateRequestDto requestDto) {
        return usersService.update(oauthId, requestDto);
    }

    @Operation(summary = "OauthId로 유저 정보 불러오기")
    @GetMapping("/{oauthId}")
    public UsersResponseDto findByOauthId(@PathVariable String oauthId) {
        return usersService.findByOauthId(oauthId);
    }

    @Operation(summary = "OauthId로 유저 Id 불러오기")
    @GetMapping("/findUserId/{oauthId}")
    public Long findUserIdByOauthId(@PathVariable String oauthId) {
        return usersService.findUserIdByOauthId(oauthId);
    }

    @Operation(summary = "nickname 중복확인")
    @GetMapping("/nicknameCheck/{nickname}")
    public boolean findByNickname(@PathVariable String nickname) {
        return usersService.findByNickname(nickname);
    }

    @Operation(summary = "다른 유저의 정보 ID로 불러오기 (제한된 정보)")
    @GetMapping("/other/{id}")
    public OtherUsersResponseDto findOtherUserById(@PathVariable Long id) {
        return usersService.findById(id);
    }
}
