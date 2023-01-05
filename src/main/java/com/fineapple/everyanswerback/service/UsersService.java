package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.users.dto.UsersResponseDto;
import com.fineapple.everyanswerback.web.users.dto.UsersSaveRequestDto;
import com.fineapple.everyanswerback.web.users.dto.UsersUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public Long save(UsersSaveRequestDto requestDto) {
        return usersRepository.save(requestDto.toEntity()).getUserId();
    }

    @Transactional
    public Long update(Long id, UsersUpdateRequestDto requestDto) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));

        users.update(requestDto);

        return id;
    }

    public UsersResponseDto findById(Long id) {
        Users entity = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));

        return new UsersResponseDto(entity);
    }
}
