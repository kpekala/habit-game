package com.kpekala.habitgame.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullName(String username);
    Optional<User> findByEmailAddress(String emailAddress);
    Boolean existsByFullName(String username);
    Boolean existsByEmailAddress(String email);
}
