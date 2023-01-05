package com.fineapple.everyanswerback.web.users;

import com.fineapple.everyanswerback.service.UsersService;
import com.fineapple.everyanswerback.web.users.dto.UsersResponseDto;
import com.fineapple.everyanswerback.web.users.dto.UsersSaveRequestDto;
import com.fineapple.everyanswerback.web.users.dto.UsersUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UsersApiController {

    private final UsersService usersService;

    @PostMapping("/api/v1/users")
    public Long save(@RequestBody UsersSaveRequestDto requestDto) {
        return usersService.save(requestDto);
    }

    @PutMapping("/api/v1/users/{id}")
    public Long update(@PathVariable Long id, @RequestBody UsersUpdateRequestDto requestDto) {
        return usersService.update(id, requestDto);
    }

    @GetMapping("/api/v1/users/{id}")
    public UsersResponseDto findById(@PathVariable Long id) {
        return usersService.findById(id);
    }
}
