package com.fineapple.everyanswerback.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT u FROM Users u WHERE u.oauthId = ?1 AND u.isDelete = false")
    Optional<Users> findByOauthId(String oauthId);

    @Query(value = "SELECT u FROM Users u WHERE u.nickname = ?1 AND u.isDelete = false")
    Optional<Users> findByNickname(String nickname);
}
