package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.dto.UsersSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(UsersSaveRequestDto requestDto) {
        return usersRepository.save(requestDto.toEntity()).getId();
    }
}
