package com.fineapple.everyanswerback.service;

import com.fineapple.everyanswerback.domain.deptClass.DeptClass;
import com.fineapple.everyanswerback.domain.deptClass.DeptClassRepository;
import com.fineapple.everyanswerback.domain.users.Users;
import com.fineapple.everyanswerback.domain.users.UsersRepository;
import com.fineapple.everyanswerback.web.users.dto.UsersResponseDto;
import com.fineapple.everyanswerback.web.users.dto.UsersSaveRequestDto;
import com.fineapple.everyanswerback.web.users.dto.UsersUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final DeptClassRepository deptClassRepository;

    @Transactional
    public Long save(UsersSaveRequestDto requestDto) {
        DeptClass deptClass = deptClassRepository.findById(requestDto.getDeptId())
                .orElseThrow(() -> new IllegalArgumentException("해당 전공분류가 존재하지 않습니다. id=" + requestDto.getDeptId()));

        return usersRepository.save(requestDto.toEntity(deptClass)).getUserId();
    }

    @Transactional
    public Long update(Long id, UsersUpdateRequestDto requestDto) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));

        DeptClass deptClass = deptClassRepository.findById(requestDto.getDeptId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 전공분류가 존재하지 않습니다. id=" + requestDto.getDeptId()));

        users.update(requestDto, deptClass);

        return id;
    }

    public UsersResponseDto findById(Long id) {
        Users entity = usersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id=" + id));

        return new UsersResponseDto(entity);
    }

    public Long findByOauthId(String oauthId) {
        Optional<Users> entity = usersRepository.findByOauthId(oauthId);

        if (entity.isPresent()) {
            return entity.get().getUserId();
        } else {
            return -1L;
        }
    }

    public boolean findByNickname(String nickname) {
        Optional<Users> entity = usersRepository.findByNickname(nickname);

        return entity.isPresent();
    }
}
